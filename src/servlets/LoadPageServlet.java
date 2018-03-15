package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Website;
import utility.StringConstants;

/**
 * Servlet implementation class LoadPageServlet
 */
@WebServlet("/LoadPageServlet")
public class LoadPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filename = (String)request.getParameter("webpage");
		Website website = (Website)request.getSession().getAttribute(StringConstants.WEBSITE);
		String contents = website.ReadAnyPageContents(filename);
		response.getWriter().write(contents);
	}


}
