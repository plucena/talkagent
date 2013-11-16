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
 * Please report bugs to the Author,  Gang Zhang (gzhangx@hotmail.com)
 * http://www.geocities.com/gzhangx/
 *
 */

/////////////////////////////////////////////////////////////////////////////////
//
//  File:		custMsg.java
//  Author:	Gang Zhang
//  E-mail:	gzhangx@hotmail.com,
//  Homepage:http://www.geocities.com/gzhangx
//
//  Description:
//	  Customer msg leave handler
//        This is just a very simple servlet for leaving messages
//  Usage:
//    need msgFile=filename parameter.  If not provided it uses default custMsg.html file
//    example: msgFile=C:\\WIN\\Desktop\\upload\\httpmsgs\\httpmsg.html
//
//
/////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;




public
class custMsg extends HttpServlet
{
  ServletConfig m_config;
  private String fileName="";
  private java.io.File theFile;
  public void init(ServletConfig config)
    throws ServletException
  {
          super.init(config);
          m_config=config;
          //Enumeration initParams = getInitParameterNames();
          fileName = config.getInitParameter("msgFile");
          if(fileName==null)fileName="custMsg.html";
          theFile=new java.io.File(fileName);
  }

  public void destroy() {
  }

  private synchronized void showMsgs(PrintWriter toClient){
    if(theFile.exists()==false){
      toClient.println("no record found<br>");
      return;
    }
    try{
      java.io.Reader  fis=(new java.io.InputStreamReader(new FileInputStream(theFile)));
      try{
        char[] buf=new char[1024];
        while(true){
          int len=fis.read(buf,0,buf.length);
          if(len<=0)break;
          toClient.write(buf,0,len);
        }
      }catch(IOException e){
        toClient.println("error while reading file: "+e+"<BR>");
      }
      fis.close();
    }catch(Exception e){
      toClient.println("Sorry, error: "+e+"<BR>");
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
  {
    res.setContentType("text/html");

    //Get the response's PrintWriter to return text to the client.
    PrintWriter toClient = res.getWriter();
    if(toClient==null)return;

    toClient.println("<html>");
    toClient.println("<head><title>Bajie's Message Page</title></head>");
    toClient.println("<body>");
    toClient.println("<center><H1>Welcome to my message page</H1></center><br><br>");
    toClient.println("<hr><BR>");
    showMsgs(toClient);
    toClient.println("<BR>");
    toClient.println("</body></html>");
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
  {

    // first, set the "content type" header of the response
    res.setContentType("text/html");

    //Get the response's PrintWriter to return text to the client.
    PrintWriter toClient = res.getWriter();
    if(toClient==null)return;

    toClient.println("<html>");
    toClient.println("<head><title>Thank you!</title></head>");
    toClient.println("<body>");

    Enumeration values = req.getParameterNames();

    if(values!=null){
      FileOutputStream fos=null;
      try{
      fos=new FileOutputStream(fileName,true);
      //String rhost=req.getRemoteHost();
      //if(rhost==null)rhost="";
      //else rhost=":"+rhost;
      String resStr="Message from: "+req.getRemoteAddr()+"<BR>\r\n";
      fos.write(resStr.getBytes());
      //toClient.println(resStr);
      resStr="Time: "+Calendar.getInstance().getTime()+"<BR>\r\n";
      fos.write(resStr.getBytes());
      //toClient.println(resStr);

      while(values.hasMoreElements()) {
        Object obj1=values.nextElement();
				String name = (String)obj1;
				if(name==null){
					toClient.println("Warning: no name<br>");
					break;
				}
				String valuea[] = req.getParameterValues(name);
				String value="";
				if(valuea!=null){
					value=valuea[0];
				}
        try{
          if(name.equals("message")==false){
            resStr=name+": "+value+"<BR>\r\n";
            fos.write(resStr.getBytes());
            toClient.println(name + ": " + value+"<br>");
          }
        }catch(IOException e){
          toClient.println("Error: "+e+"<BR>");
        }
      }
      try{
        String valuea[] = req.getParameterValues("message");
        String msg="";
        if(valuea!=null)msg=valuea[0];
        resStr="Message:<br>\r\n<table border=1 width=80%><tr><td>"+msg+"</td></tr></table><br>";
        fos.write(resStr.getBytes());
        toClient.println(resStr);
      }catch(Exception e){}
      resStr="<hr><BR>\r\n";
      fos.write(resStr.getBytes());
      fos.close();
      }catch(Exception e){
        toClient.println("Please contact Adm, error "+e+"<BR>");
      }finally{
        try{fos.close();}catch(Exception e){}
      }
    }

    toClient.println("<BR>");
    toClient.println("<br>Thank you!<br>");
    toClient.println("</body></html>");
  }


}

