

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Verify.VerifyRecaptcha;
import GetToken.GetToken_SEAS;


/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String password = request.getParameter("password");
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		
		
		VerifyRecaptcha VerifyOBJ= new VerifyRecaptcha();
		GetToken_SEAS   SeasOBJ= new GetToken_SEAS();
		boolean verify1 = VerifyOBJ.verify(gRecaptchaResponse);
		
		PrintWriter out = response.getWriter();
		
		out.println("Your UserName is : "+uname +" , \n Your password is : "+password);
		
		//out.println(gRecaptchaResponse);
		
		if (verify1) {
			
			String response1 = SeasOBJ.SEAS_Response(uname,password,"Garanti_Bank_User_Auth");
			out.println("\n\n Response From SEAS is \n\n"+response1);
			
			
			
		} else {
			
	        out.println("\n\nYou missed the Captcha");
		}
				
		/*if (uname.equals("Kashyap") && password.equals("1234"))
				{
			     
			      response.sendRedirect("Welcome.jsp");
				}
		else
		{
			response.sendRedirect("Error.jsp");
		}*/
	}

}
