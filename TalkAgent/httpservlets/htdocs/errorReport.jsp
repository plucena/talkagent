<html>
<head><title>Error Happened</title></head>
<body bgcolor=#aaaaee>
<center><H1>Error Happened</H1></center><br><br>
<hr><BR>
<%
    
    out.println("<pre>");
    Throwable e=(Throwable)request.getAttribute("javax.servlet.error.Throwable");
    
    if(e!=null){
        e.printStackTrace(new java.io.PrintWriter(out));
    }
    
    if(e==null){
      String errorMsg =(String) request.getAttribute("javax.servlet.error.message");
      out.println(errorMsg);
    }
    out.println("</pre>");

%>
<BR>
<BR><hr>
<font size=-2>page generated by errorReport servlet</font>
</body></html>

