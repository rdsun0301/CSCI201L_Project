package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;
import data.Website;
import utility.StringConstants;
import webHosting.Webhost;

/**
 * Servlet implementation class DeleteWebpageServlet
 */
@WebServlet("/DeleteWebpageServlet")
public class DeleteWebpageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteWebpageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Delete request received");
		Webhost myWebhost = new Webhost (StringConstants.GITURL, StringConstants.LOCALREPO,
				StringConstants.GITUSERNAME, StringConstants.GITPASSWORD);
		Website website = (Website)request.getSession().getAttribute(StringConstants.WEBSITE);
		User user = (User)request.getSession().getAttribute(StringConstants.CURRENTUSER);
		String webpage = request.getParameter("webpage");
		myWebhost.deleteWebpage(user.getUsername(), website.getName(), webpage);
	}



}
