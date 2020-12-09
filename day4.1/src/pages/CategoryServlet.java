package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojos.Customer;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in do-get of " + getClass().getName());
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			pw.print("<h4>Successful Login</h4>");
			//get user details from curnt request scope
			Customer c=(Customer)request.getAttribute("clnt_info");
			if(c != null) //request scoped attr exists
				pw.print("<h5> User Details from Request scope "+c+" </h5>");
			else //no session data
				pw.print("<h5> No request scoped attrs</h5>");
		
		}
	}

}
