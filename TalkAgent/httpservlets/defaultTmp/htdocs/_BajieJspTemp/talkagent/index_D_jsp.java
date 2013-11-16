import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import java.beans.*;
import java.util.*;
public class index_D_jsp extends com.BajieSoft.jspCompiler._jsp_gzJspServlet {
public void _jspService(HttpServletRequest request, HttpServletResponse  response) throws IOException, ServletException 
{
    JspFactory _jsp_BjFactory = null;
    PageContext pageContext = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    Exception exception=null;
    try{
      _jsp_BjFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jsp_BjFactory.getPageContext(this,                            request,response, null, true, 8192, true);
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      HttpSession session = pageContext.getSession();
      out = pageContext.getOut();
      exception =pageContext.getException();
out.print("<!-- Copyright (c) 2002 by ObjectLearn. All Rights Reserved. -->\n\n<html>\n<head></head>\n\n<body>\n<table border=\"0\" width=\"100%\">\n  <tr>\n    <td width=\"100%\" bgcolor=\"#3333FF\"><font size=\"4\" color=\"#FFFFFF\"><b>TalkAgent\n      0.1</b></font></td>\n  </tr>\n  <tr>\n    <td width=\"100%\"></td>\n  </tr>\n  <tr>\n    <td width=\"100%\">\n      <ul>\n        <li><a href=\"./agent\">Run Agent</a></li>\n        <li><a href=\"./agent?param=ams\">Configure Agent</a></li>\n      </ul>\n    </td>\n  </tr>\n</table>\n</body>\n</html>");
    }catch (Throwable _jsp_ex) {
      getServletConfig().getServletContext().log(getServletConfig().getServletName(),_jsp_ex);
      _jsp_ex=com.BajieSoft.jspCompiler.gzJspComponent.mapJspLN("defaultTmp/htdocs/_BajieJspTemp/talkagent/index_D_jsp.java",_jsp_ex);
      pageContext.handlePageException(_jsp_ex);
    } finally {
      _jsp_BjFactory.releasePageContext(pageContext);
    }
}
public String[] _bjSrvGetJspDepNames(){return null;}
public long[] _bjSrvGetJspTimes(){return null;}
}