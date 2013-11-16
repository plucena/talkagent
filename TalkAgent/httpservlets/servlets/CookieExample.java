/* CookieExample.java, modified from JSDK2.1, java.sun.com
 * @author James Duncan Davidson <duncan@eng.sun.com>
 * Modified by Gang for bjserver testing
 * Please note that 
 */

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Example servlet showing request headers
 *
 * @author James Duncan Davidson <duncan@eng.sun.com>
 */

public class CookieExample extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");

        String cookieName = request.getParameter("cookiename");
        String cookieValue = request.getParameter("cookievalue");
        String cookieExp=request.getParameter("cookieexp");
        int exp=-1;
        if(cookieExp!=null){
          try{
            exp=Integer.parseInt(cookieExp);
          }catch(Exception e){
          }
        }

        //!!!! GZ: I implement the server so cookies can only be set before getWriter 
        //!!!! all resonse cookies must be set before you do getWriter or getOutputStream 
        //!!!! on response
        if ( (cookieName != null) && (cookieValue != null) ) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
		      String dom=request.getParameter("cookiedom");
          String pth=request.getParameter("cookiepath");
          if(pth!=null)cookie.setPath(pth);
		      String comm=request.getParameter("cookiecomment");
		      if(dom!=null)if(dom.equals("")==false)cookie.setDomain(dom);
		      if(comm!=null)if(comm.equals("")==false)cookie.setComment(comm);
          if(exp!=-1)cookie.setMaxAge(exp);
            response.addCookie(cookie);
        }

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body bgcolor=\"white\">");
        out.println("<head>");

        ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");
        String title = rb.getString("cookies.title");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h3>" + title + "</h3>");

        Cookie[] cookies = request.getCookies();
        if ( (cookies!=null) && (cookies.length > 0 )) {
            out.println(rb.getString("cookies.cookies") + "<br>");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                out.print("Cookie Name: " + cookie.getName() + "<br>");
                out.println("  Cookie Value: " + cookie.getValue() +
			    "<br><br>");
            }
        } else {
            out.println(rb.getString("cookies.no-cookies"));
        }

        
        if (cookieName != null && cookieValue != null) {
            out.println("<P>");
            out.println(rb.getString("cookies.set") + "<br>");
            out.print(rb.getString("cookies.name") + "  " + cookieName +
		      "<br>");
            out.print(rb.getString("cookies.value") + "  " + cookieValue);
        }
  

        
        out.println("<P>");
        out.println(rb.getString("cookies.make-cookie") + "<br>");
        out.print("<form action=\"");
        out.println("CookieExample\" method=POST>");
        out.print(rb.getString("cookies.name") + "  ");
        out.println("<input type=text length=20 name=cookiename><br>");
        out.print(rb.getString("cookies.value") + "  ");
        out.println("<input type=text length=20 name=cookievalue><br>");
 
        out.print("Cookie path  ");
	      out.println("<input type=text length=20 name=cookiepath><br>");
	      out.print("Cookie domain  ");
	      out.println("<input type=text length=20 name=cookiedom><br>");
	      out.print("Cookie Comment  ");
	      out.println("<input type=text length=20 name=cookiecomment><br>");
  
        out.print("Cookie Expires (in second)  ");
	      out.println("<input type=text length=20 name=cookieexp><br>");
	

        out.println("<input type=submit></form>");
            
            
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request, response);
    }

}


