/*
//  File  : UploadServlet.java
//  Author: Gang Zhang
//  Date  : 06/01/1999
//  Desc  : Servlet that loads file to server
//  Revisions:  GZ, 10/08/1999, speed ups

//  Desc:  just a real simple uploader.
//
//  reference:  http://www.cis.ohio-state.edu/rfc/rfc2388.txt
//
//Debug GZ: when diling with mutipart content, with binary files,
    <form ENCTYPE="multipart/form-data" action=http://localhost/servlets/SurveyServlet method=POST>
      <input type=hidden name=survey value=Survey01Results>
	<INPUT TYPE="file" NAME="userfile" SIZE=40><BR>

   getParameterName will return a enumator with no keys....

   Also, we must read the input, otherwise, error will happen....
   //to use, call gp.parse(), then
   //len=gp.readFile();
   //fo.write(gp.getBuf(),0,i);, continue till len<0
//
*/

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.BajieSoft.util.gzMultiMimeParser;

public class UploadServlet extends HttpServlet
{
  ServletConfig m_config;
  private File resultsDir=null;
  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    m_config=config;
    //Jul 25, 2002  removed to force it to use temp dir for security reasons
    //              if you wish to customize, either edit servlet's property 
    //              or add a UploadServlet.params.properties file in the same directory
    //              and add line resultsDir=dir in the file.  Make sure the directory
    //              is not in web tree, else people can upload jsp file and execute them!!
    //
    //String resultsDirStr = getInitParameter("resultsDir");
    //if(resultsDirStr==null)
      resultsDir=(File)config.getServletContext().getAttribute("javax.servlet.context.tempdir");
    //else
    //  resultsDir=new File(resultsDirStr);
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
   throws ServletException, IOException
  {
    PrintWriter toClient = res.getWriter();
    toClient.println("<br>get operation!!<br>");
    doPost(req,res);
  }
  /**
   * Write survey results to output file in response to the POSTed
   * form.  Write a "thank you" to the client.
   */
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
    toClient.println("<body bgcolor=#aaaaee>");

    try{
      gzMultiMimeParser gp=new gzMultiMimeParser(req.getInputStream());
      while(true){
        try{
          if(gp.parse()==false)break;
        }catch(Exception e){break;}

      /*
      Enumeration en=gp.getKeys();
      while(en.hasMoreElements()){
        String name=(String)en.nextElement();
        String value=gp.getValue(name);
        toClient.println("name= "+name+" :: "+value+"<br>");
      }
      */

      if(gp.getContentType()!=null){
        String fullOrigFilename=gp.getValue("filename");
        toClient.println("filename= "+fullOrigFilename+"<br>");
        StringTokenizer stk=new StringTokenizer(fullOrigFilename,"\\");
        String fname="";
        while(stk.hasMoreElements()){
          fname=(String)stk.nextElement();
          if(stk.hasMoreElements()==false)break;
        }
        if(fname.length()==0)continue;
        //String uploadFilePath=resultsDir+req.getRemoteAddr()+"_"+req.getRemoteHost()+"_"+fname;
        File uploadFilePath=new File(resultsDir,fname);
        FileOutputStream fo=new FileOutputStream(uploadFilePath);
        synchronized(this){
        java.io.Writer  logger=null;
          try{
          logger=new java.io.OutputStreamWriter(new FileOutputStream("uploads.log"));
          logger.write(req.getRemoteAddr()+":"+req.getRemoteHost()+" => "+uploadFilePath);
          }catch(Exception e){}
          finally{
            try{logger.close();}catch(Exception e){}
          }

        }
        //commented out for more security
        toClient.println("Recording file to ::"+uploadFilePath+"<br>");
        toClient.flush();
        long startTime=System.currentTimeMillis();
        long totalfilelen=0;
        totalfilelen=gp.WritePutFile(fo);
        fo.close();
        toClient.println("<br>File length is "+totalfilelen+" bytes<br>");
        long totalTime=System.currentTimeMillis()-startTime;
        if(totalTime==0)totalTime=1;
        toClient.println("<br>speed is "+(totalfilelen*1000/totalTime)+" bytes/sec<br>");
      }
      //toClient.println("<br>read file is :<br>\n<pre>"+s+"</pre>");
      }
    }catch(Throwable e){
      e.printStackTrace();
      toClient.println("<br>Warning: Can't write file "+e+"<br>");
    }

    try{
    toClient.println("<br>Remote IP is "+req.getRemoteAddr());
    if(req.getRemoteHost()!=null)
      toClient.println("Remote host: "+req.getRemoteHost()+"<br>");
    toClient.println("<br>size is :"+req.getContentLength()+"<BR>");
    //toClient.println("<title>Thank you!</title>");
    toClient.println("<br>Thank you for your uploading!<br>");
    toClient.println("</body></html>");

    // Close the writer; the response is done.
    toClient.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}

