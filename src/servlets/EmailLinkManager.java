package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import email.Action;
import email.EmailScheme;
import utility.StringConstants;

/**
 * Servlet implementation class EmailLinkManager
 */
@WebServlet("/EmailLinkManager")
public class EmailLinkManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailLinkManager() {
        super();
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String actionId = request.getParameter(StringConstants.ACTIONID);
		String actionName = request.getParameter(StringConstants.ACTIONNAME);
		
		Action action = EmailScheme.GetAction(actionId);
		action.execute();
		
		if(actionName.equals(StringConstants.SIGNUPACTION)) {
			request.setAttribute("errorsignup", "Sign up successful!");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		} else if(actionName.equals(StringConstants.DELETEACCOUNTACTION)) {
			request.setAttribute("errorsignup", "Account Deleted Successfully!");
			request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		} else {
			request.setAttribute("confirmation", "Changes Saved!");
			request.getRequestDispatcher("jsp/settings.jsp").forward(request, response);
		}
	}
}
