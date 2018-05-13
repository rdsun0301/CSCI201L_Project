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
import utility.StringConstants;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Database db = new Database(new MongoClient(new MongoClientURI(StringConstants.DATAURL)), "test");
		User user = db.ReadFromUsersCollection(username);
		if(user == null) {
			request.setAttribute("errorlogin", "Invalid username or password.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else if(!(user.CheckValidPassword(password))) {
			request.setAttribute("errorlogin", "Invalid username or password.");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		}
		else {
			request.getSession().setAttribute(StringConstants.CURRENTUSER, user);
			request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
		}
	}

}
