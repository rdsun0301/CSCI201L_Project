package servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import data.Database;
import data.Website;
import utility.StringConstants;

/**
 * Servlet implementation class AddWebpage
 */
@WebServlet("/AddWebpage")
public class AddWebpage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddWebpage() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database(new MongoClient(new MongoClientURI(StringConstants.DATAURL)), "test");
		
		String websiteName = (String) request.getParameter(StringConstants.WEBSITE);
		String newWebpageName = (String) request.getParameter(StringConstants.NEWWEBPAGE);
		System.out.println("addPage "+websiteName+" "+newWebpageName);
		Website website = db.ReadFromWebsitesCollection(websiteName);
		String path = website.getDirectory();
		String webpagePath = path + "/" + newWebpageName+".html";
		File dest = new File(webpagePath);
		dest.createNewFile();
		File template = new File(getServletContext().getRealPath(StringConstants.GUESTPAGE));
		try {
			FileUtils.copyFile(template, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//String contents = website.ReadAnyPageContents(newWebpageName).replace("\n",  "").replace("\r", "");
		//System.out.println(contents);
		//request.getSession().setAttribute(StringConstants.CONTENTS, contents);
		//request.getRequestDispatcher("jsp/editPage.jsp").forward(request, response);

	}
}
