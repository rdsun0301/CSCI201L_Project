package servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class AddWebsite
 */
@WebServlet("/AddWebsiteServlet")
public class AddWebsiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWebsiteServlet() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
		User user = (User)request.getSession().getAttribute(StringConstants.CURRENTUSER);
		String websiteName = (String)(request.getParameter("newWebsite"));
		System.out.println("new Website name: "+ websiteName);


		if(websiteName == "") {
			request.setAttribute("error", "Please enter a name for the new website.");
			request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
		}
		else if (user.isWebsite(websiteName)) {
			request.setAttribute("error", "Website name already in use!");
			request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
		}
		else {
			request.setAttribute("error", "");
			//Construct the website
			Website myWebsite = new Website(getServletContext().getRealPath(StringConstants.TEMPLATE), StringConstants.LOCALREPO, user.getUsername(), websiteName);
			//Update the database todo
			user.AddWebsite(myWebsite);
			System.out.println("ID!!!"+user.getId());
			//add new website to database
			db.WriteToWebsitesCollection(myWebsite);
			//update existing user
			db.UpdateUser(user);
			//request.getSession().setAttribute(StringConstants.CURRENTUSER, user);
			response.sendRedirect("jsp/dashboard.jsp");
		}
	}

}
