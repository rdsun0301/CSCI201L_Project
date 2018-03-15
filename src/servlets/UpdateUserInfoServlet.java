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
import email.*;
import email.EmailScheme;
import utility.RandomStringGenerator;
import utility.StringConstants;

/**
 * Servlet implementation class UpdateUserInfoServlet
 */
@WebServlet("/UpdateUserInfoServlet")
public class UpdateUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
		User user = (User)request.getSession().getAttribute(StringConstants.CURRENTUSER);
		
		EmailScheme emailscheme = new EmailScheme();
		RandomStringGenerator rsg = new RandomStringGenerator();
		String randomString = rsg.generate();
		
		String actionName = request.getParameter("action");
		if(actionName.equals("fname")) {
			String fname = request.getParameter("fname");
			ChangeFirstNameAction action = new ChangeFirstNameAction(user, db, fname);
			emailscheme.SendMessage(randomString, action, StringConstants.CHANGEFIRSTNAMEACTION, user.getEmail());
		} else if(actionName.equals("lname")) {
			String lname = request.getParameter("lname");
			ChangeLastNameAction action = new ChangeLastNameAction(user, db, lname);
			emailscheme.SendMessage(randomString, action, StringConstants.CHANGELASTNAMEACTION, user.getEmail());
		} else if(actionName.equals("password")) {
			String password = request.getParameter("password");
			ChangePasswordAction action = new ChangePasswordAction(user, db, password);
			emailscheme.SendMessage(randomString, action, StringConstants.CHANGEPASSWORDACTION, user.getEmail());
		} else if(actionName.equals("email")) {
			String email = request.getParameter("email");
			ChangeEmailAction action = new ChangeEmailAction(user, db, email);
			emailscheme.SendMessage(randomString, action, StringConstants.CHANGEEMAILACTION, user.getEmail());
		} else if(actionName.equals("delete")) {
			DeleteAccountAction action = new DeleteAccountAction(user, db);
			emailscheme.SendMessage(randomString, action, StringConstants.DELETEACCOUNTACTION, user.getEmail());
		}
	}	
}
