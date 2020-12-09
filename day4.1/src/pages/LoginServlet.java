package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDaoImpl;
import pojos.Customer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(value = "/validate", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDaoImpl customerDao;

	// overriding form of the method can't throw any BROADER checked excs
	public void init() throws ServletException {
		try {
			customerDao = new CustomerDaoImpl();
		} catch (Exception e) {
			// inform WC that init failed : How to : throw servlet exc : wrapping err mesg n
			// root cause
			throw new ServletException("err in init ", e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	// overriding form of the method can't throw any NEW checked excs
	public void destroy() {
		// dao's clean up
		try {
			customerDao.cleanUp();
		} catch (SQLException e) {
			System.out.println("err in destroy " + e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// set cont type
		response.setContentType("text/html");
		try (PrintWriter pw = response.getWriter()) {
			// get email n password from clnt : HttpServletRequest
			String email = request.getParameter("em");
			String password = request.getParameter("pass");
			// invoke Dao's method : for authenticating user.
			Customer authenticateCustomer = customerDao.authenticateCustomer(email, password);
			// chk if valid or invalid
			if (authenticateCustomer == null) {
				// invalid login : send a retry link to the clnt
				pw.print("<h4>Invalid Login</h4>");
				pw.print(" <h4 align='center'><a href='login.html'>Please Retry</a></h4>");

			} else {
				// login successful
				pw.print("from login servlet");
			//	pw.flush();
				// add authenticated customer details in current request scope (min scope
				// required in server pull / request dispatching / resource chaining)
				request.setAttribute("clnt_info", authenticateCustomer);// string , object : scope of clnt_info : curnt request only
			   //server pull navigation
				//1st step : get Req dispatcher obj : wrapper to wrap the next resource : category servlet
				//arg : path (url pattern of the next servlet)
				RequestDispatcher dispatcher = request.getRequestDispatcher("category");
				//forward 
				dispatcher.forward(request, response);//forwarding the clnt to the next page in SAME request
				System.out.println("after forward");

			} // WC clears (discard PW's buffer n then navigates the clnt to the next page) : Invokes 
			//CategoryServlet's doPost (doPost ---->forwarded --->doPost)
			

		} catch (Exception e) {
			throw new ServletException("err in do-get", e);
		}
	}

}
