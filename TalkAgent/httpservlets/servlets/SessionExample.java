/* $Id: SessionExample.java,v 1.1 2002/11/27 16:31:29 cvs Exp $
 * Please note that this file is the property of sun, java.sun.com
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

public class SessionExample extends HttpServlet {

    ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");
    
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
        String title = rb.getString("sessions.title");
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body>");

        // img stuff not req'd for source code html showing
	// relative links everywhere!
        out.println("<h3>" + title + "</h3>");

        if(session!=null){
          out.println(rb.getString("sessions.id") + " " + session.getId());
          out.println("<br>");
          out.println(rb.getString("sessions.created") + " ");
          out.println(new Date(session.getCreationTime()) + "<br>");
          out.println(rb.getString("sessions.lastaccessed") + " ");
          out.println(new Date(session.getLastAccessedTime()));

        

          out.println("<P>");
          out.println(rb.getString("sessions.data") + "<br>");
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
        out.print("SessionExample\" ");
        out.println("method=POST>");
        out.println(rb.getString("sessions.dataname"));
        out.println("<input type=text size=20 name=dataname>");
        out.println("<br>");
        out.println(rb.getString("sessions.datavalue"));
        out.println("<input type=text size=20 name=datavalue>");
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
