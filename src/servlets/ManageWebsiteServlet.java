package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import data.Database;
import data.User;
import data.Website;
import utility.StringConstants;
import webHosting.Webhost;

/**
 * Servlet implementation class ManageWebsiteServlet
 */
@WebServlet("/ManageWebsiteServlet")
public class ManageWebsiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageWebsiteServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String websiteName = request.getParameter("website");
		//System.out.println(websiteName);
		Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
		User currentUser = (User)request.getSession().getAttribute(StringConstants.CURRENTUSER);
		String action = request.getParameter("action");
		if(action.equals("edit")) {
			Website website = db.ReadFromWebsitesCollection(websiteName);
			request.getSession().setAttribute(StringConstants.WEBSITE, website);
			
			String contents = website.ReadMainPageContents().replace("\n",  "").replace("\r", "");
			request.getSession().setAttribute(StringConstants.CONTENTS, contents);
			
			File directory = new File(website.getDirectory());
			File[] files = directory.listFiles();
			Vector<String> names = new Vector<String>();
			for(int i = 0; i < files.length;i++){
				String fileName = files[i].getName();
				//System.out.println(fileName);
				String type = fileName.substring((fileName.indexOf('.')+1));
				if(type.equals("html")){
					names.add(fileName.substring(0, fileName.indexOf('.')));
				}
			}
			request.getSession().setAttribute("fileNames", names);

			request.getRequestDispatcher("jsp/editPage.jsp").forward(request, response);
		} else if(action.equals("share")) {
			String sharedUser = request.getParameter("username");
			if(sharedUser.equals(currentUser.getUsername())) {
				response.sendError(HttpServletResponse.SC_CONFLICT);
			}
			//Check if user exists
			else if (db.ReadFromUsersCollection(sharedUser)!=null) {
				Website site = db.ReadFromWebsitesCollection(websiteName);
				if(site.isSharedUser(sharedUser)) {
					response.sendError(600);
				}
				else {
					//Make changes to the user
					currentUser.shareWebsite(websiteName, sharedUser);
					//Make changes to website
					site.addSharedUser(sharedUser);
					//Update database
					db.UpdateWebsite(site);
					//update sharer's websites
					db.UpdateUser(currentUser);
					//update sharee's websites
					User share_ee = db.ReadFromUsersCollection(sharedUser);
					share_ee.AddWebsite(site);
					db.UpdateUser(share_ee);
					request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		} else if(action.equals("publish")) {
			Website site = db.ReadFromWebsitesCollection(websiteName);
			//update in website vector
			site.setIsPublished(true);
			db.UpdateWebsite(site);
			//update for owner

			currentUser.publishWebsite(site.getName());
			db.UpdateUser(currentUser);
			//update for shared users
			for (String shareeName: site.getSharedUsers()) {
				User sharee = db.ReadFromUsersCollection(shareeName);
				sharee.publishWebsite(site.getName());
				db.UpdateUser(sharee);
			}
			Webhost myWebhost = new Webhost (StringConstants.GITURL, StringConstants.LOCALREPO,
					StringConstants.GITUSERNAME, StringConstants.GITPASSWORD);
			myWebhost.pushChanges(currentUser.getUsername(), site.getName(), "friday changes");
			request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
		} else if(action.equals("delete")) {
			Website site = db.ReadFromWebsitesCollection(websiteName);
			
			//Delete website from shared users' list
			for (String shareeName: site.getSharedUsers()) {
				User sharee = db.ReadFromUsersCollection(shareeName);
				sharee.deleteWebsite(site.getName());
				db.UpdateUser(sharee);
			}
			//Delete website from owner's list
			currentUser.deleteWebsite(site.getName());
			db.UpdateUser(currentUser);
			
			//Remove from websites list
			db.DeleteWebsite(site);

			Webhost myWebhost = new Webhost (StringConstants.GITURL, StringConstants.LOCALREPO,
					StringConstants.GITUSERNAME, StringConstants.GITPASSWORD);
			myWebhost.deleteWebsite(currentUser.getUsername(), site.getName());
			//Reload dashboard
			//request.getSession().setAttribute(StringConstants.CURRENTUSER, currentUser);
			request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
		}
		
	}

}
