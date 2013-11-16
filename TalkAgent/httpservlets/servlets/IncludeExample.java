
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Example servlet showing request dispatcher
 *
 * @author Gang Zhang
 */

public class IncludeExample extends HttpServlet {
  private ServletConfig mConfig;
  public void init(ServletConfig config)
	throws ServletException
	{
		super.init(config);
		mConfig=config;
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws IOException, ServletException
  {
      response.setContentType("text/html");


      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<head>");

      ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");
      String title = rb.getString("include.title");
      out.println("<title>" + title + "</title>");
      out.println("</head>");
      out.println("<body>");

      out.println("<h3>" + title + "</h3>");

      try{
        RequestDispatcher dp=mConfig.getServletContext().getRequestDispatcher("/servlet/CookieExample");
        dp.include(request,response);
      }catch(Exception e){
        out.println("error "+e);
      }
        
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


