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
import email.Action;
import email.EmailScheme;
import email.SignUpAction;
import utility.RandomStringGenerator;
import utility.StringConstants;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
		User user = db.ReadFromUsersCollection(username);
		if(user != null) {
			request.setAttribute("errorsignup", "Username already in use.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(firstname.equals("")) {
			request.setAttribute("errorsignup", "Please fill in all the fields.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(lastname.equals("")) {
			request.setAttribute("errorsignup", "Please fill in all the fields.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(email.equals("")) {
			request.setAttribute("errorsignup", "Please fill in all the fields.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(username.equals("")) {
			request.setAttribute("errorsignup", "Please fill in all the fields.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(password.equals("")) {
			request.setAttribute("errorsignup", "Please fill in all the fields.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else {
			User user2 = new User(firstname, lastname, email, username, password);
			Action sua = new SignUpAction(user2, db);
			EmailScheme emailscheme = new EmailScheme();
			
			RandomStringGenerator rsg = new RandomStringGenerator();
			String randomString = rsg.generate();
			
			emailscheme.SendMessage(randomString, sua, StringConstants.SIGNUPACTION, email);
			
			request.setAttribute("errorsignup", "Confirmation email sent!");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
	}

}
