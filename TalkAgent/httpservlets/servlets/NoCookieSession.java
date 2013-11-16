/* 
 * Servlet for testing sessions wehn browser doesn't support sessions.
 * In this case the session need to be embedded in url or send as a 
 * POST parameter
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

public class NoCookieSession extends HttpServlet {
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");

        //session can't be created after response.getWrite() or getOutputStream() operation!!!
        HttpSession session = request.getSession(false);
        String dataName = request.getParameter("dataname");
        String dataValue = request.getParameter("datavalue");
        if (dataName != null && dataValue != null) {
          if(session==null){
            session = request.getSession();
          }
          session.putValue(dataName, dataValue);
        }
        
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        String title = "Session Example for browsers doesn't support sessions";
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");

        // img stuff not req'd for source code html showing
	// relative links everywhere!
        out.println("<h3>" + title + "</h3>");

        if(session!=null){
          out.println("sessions id" + " " + session.getId());
          out.println("<br>");
          out.println("session creation time ");
          out.println(new Date(session.getCreationTime()) + "<br>");
          out.println("Last access time ");
          out.println(new Date(session.getLastAccessedTime()));

        

          out.println("<P>");
          out.println("Session data<br>");
          String[] valueNames = session.getValueNames();
          if (valueNames != null && valueNames.length > 0) {
              for (int i = 0; i < valueNames.length; i++) {
                  String name = valueNames[i];
                  String value = session.getValue(name).toString();
                  out.println(name + " = " + value + "<br>");
              }
          }
        }else{
           out.println("No session<br>");
        }

        out.println("<P>");
        out.print("<form action=\"");
        out.print("NoCookieSession\" ");
        out.println("method=POST>");
        out.println("Data name");
        out.println("<input type=text size=20 name=dataname>");
        out.println("<br>");
        out.println("Data value");
        out.println("<input type=text size=20 name=datavalue>");
        if(session!=null){
          //here is where the session value is passed when cookies are not available.
          //If operation is not post, make the linke http://aaa?jsessionid=id
          out.println("<input type=hidden name=jsessionid value="+session.getId()+">");
        }
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");

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
