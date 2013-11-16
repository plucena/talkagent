/* ======================================================
 * BajieServer License
 *
 * Copyright (c) 2000 BajieSoft.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by
 *        BajieSoft (http://www.geocities.com/gzhangx/  gzhangx@hotmail.com)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL BajieSoft OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 *
 * Please report bugs to the Author,  Gang Zhang (gzhangx@hotmail.com,
 *                                             gzhangx@yahoo.com)
 * http://www.geocities.com/gzhangx/
 *
 */
/* gzAuth.java,  Author Gang Zhang, gzhangx@yahoo.com
 * simple test for WWW-Authenticate
 */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class gzAuth extends HttpServlet {

 // Base64Decorder dec64=new Base64Decorder();
  public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws IOException, ServletException
  {
    response.setContentType("text/html");

    HttpSession s=request.getSession();

    //getRemoteUser checks if the remote user is authenticated.
    //It returns null if no remote user is found.
    if(request.getRemoteUser()==null){
      //tell the browser that they need to show the authenticate box
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //Standarded protocol.  You can change Basic realm value to anything else.
      response.setHeader("WWW-Authenticate"," Basic realm=\\BajieWorld");

      //this line will not show till the authenticate timeout.
      response.getWriter().write("<b>Not authenticated</b>");
      for(int i=0;i<5;i++){
        response.getWriter().write("<!-- MS internet explorer patch.  IE use its own error page if response is too short.  However their own IIS server is not affected. -->");
      }
      //Add a return here if you don't want the user to enter the page when not authorized.
      //return;
    }


    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head>");
    ServletInputStream is=request.getInputStream() ;


    String title = "gzAuth Test (WWW-Authenticate)";
    out.println("<title>" + title + "</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h3>" + title+" Demo " + "</h3>");
    out.println("in config/users.properties, user admin, password admin and user role is admin is set<br>");
    out.println("<table border=0>");
    try{
      Enumeration e = request.getHeaderNames();
      while (e.hasMoreElements()) {
          String headerName = (String)e.nextElement();
          String headerValue = request.getHeader(headerName);
          out.println("<tr><td bgcolor=\"#CCCCCC\">" + headerName);
          out.println("</td><td>" + headerValue + "</td></tr>");
      }

      out.println("<tr><td bgcolor=\"#EEEEEE\">" + "Remote user (null if not authenticated)");
      out.println("</td><td bgcolor=\"#00FF00\">" + request.getRemoteUser() + "</td></tr>");
      out.println("<tr><td>User if role 'admin' is</td><td>"+request.isUserInRole("admin")+"</td></tr>");

      out.println("</table><br><br>");
      title = "Test for Request Params";

      out.println("<h3>" + title + "</h3>");
      e = request.getParameterNames();
      String paramName=null;
      out.println("<table>");
      while (e.hasMoreElements()) {
          paramName = (String)e.nextElement();
          String paramValue = request.getParameter(paramName);
          out.println("<tr><td bgcolor=\"#FF00FF\">" + paramName);
          out.println("</td><td bgcolor=#CCCCCC>" + paramValue + "</td></tr>");
      }
      out.println("</table>");
      if(paramName==null){
          out.println("No parameters supplied<br>");
      }

      //Here is an example of how to post some information to the server.
      out.println("<P>");
      out.print("<form action=\"/servlet/gzAuth\" method=POST>");
      out.println("Parameter 1");
      out.println("<input type=text size=20 name=P1>");
      out.println("<br>");
      out.println("Parameter 2");
      out.println("<input type=text size=20 name=P2>");
      out.println("<br>");
      out.println("<input type=submit>");
      out.println("</form>");

    }catch(Exception e){
      e.printStackTrace();
    }
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
  }

  public void doPost(HttpServletRequest request,HttpServletResponse response)
    throws IOException, ServletException
  {
      doGet(request, response);
  }

}

