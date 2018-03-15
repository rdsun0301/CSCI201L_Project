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
 * Servlet implementation class GuestServlet
 */
@WebServlet("/GuestServlet")
public class GuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String templatePath = getServletContext().getRealPath(StringConstants.TEMPLATE);
    	//Open the EditGUI
    	Website website = new Website();
    	website.setMainPage(templatePath);
		request.getSession().setAttribute(StringConstants.WEBSITE, website);
		
		String contents = website.ReadMainPageContents().replace("\n",  "").replace("\r", "");
		request.getSession().setAttribute(StringConstants.CONTENTS, contents);
		
		request.getRequestDispatcher("jsp/guestEdit.jsp").forward(request, response);
    }

}
