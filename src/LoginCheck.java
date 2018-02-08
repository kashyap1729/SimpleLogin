

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		//String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		//String urlString ="https://tiublrboaapp037.sciblr.in.ibm.com:31000/myfilegateway";
		String urlString =request.getParameter("url");
		
		
		//VerifyRecaptcha VerifyOBJ= new VerifyRecaptcha();
		GetToken_SEAS   SeasOBJ = null;
		String token = null;
		//boolean verify1 = VerifyOBJ.verify(gRecaptchaResponse);
		
		
		
		if (true) {
			
			  SeasOBJ= new GetToken_SEAS();
			try {
				SeasOBJ.connect();
				token = SeasOBJ.requestSSOToken(uname,password,"Garanti_Bank_User_Auth");
				System.out.println("Token : "+token);
			} catch (Throwable t) {
				// TODO Auto-generated catch block
				t.printStackTrace();
			} finally {
				if (SeasOBJ != null)
					SeasOBJ.disconnect();
			}
			if (token==null)
			{
			//request.setAttribute("errorMessage", "Invalid UserId Or Password");
			
			/* PrintWriter out=response.getWriter();
			  out.println("<script type=\"text/javascript\">");
		       out.println("alert('User or password incorrect');");
		       out.println("</script>");*/
		       
		       response.setHeader("Location",urlString); 
			
			//request.getRequestDispatcher("/Login.jsp").forward(request, response);
			
			}
			
			//  URL url = new URL(urlString); 
			//  HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			  
		
			 
			
			response.setHeader("SM_USER",uname);
			Cookie ssoToken = new Cookie("SSOTOKEN", token);
			
			ssoToken.setDomain("sciblr.in.ibm.com");
			ssoToken.setVersion(1);
			ssoToken.setPath("/");
			ssoToken.setMaxAge(-1);  //cookie is deleted when the browser is closed ?
			ssoToken.setHttpOnly(true);
			
			
			
			ssoToken.setSecure(true); //allows only over secure connection( allows cookie to send to SSP)
			
			response.addCookie(ssoToken);
			response.setStatus(HttpServletResponse.SC_FOUND); //302
			response.setHeader("Location", urlString);
			
	        // urlConn.setUseCaches(true); 
            //  urlConn.connect();
			//response.setHeader("Location", request.getParameter("url"));
			
			
		} else {
			
			
			//out.println("<script type=\"text/javascript\">");
			    // out.println("location='Login.jsp';");
			//response.sendRedirect("Login.jsp");
			
			//out.println("<script type=\"text/javascript\">");
			//   out.println("alert('You Missed the Captcha');");
			   
			 //  out.println("</script>");
			
			
			request.setAttribute("errorMessage", "You Missed the Captcha");
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
			
			
	        
		}
				
		
	}

}
