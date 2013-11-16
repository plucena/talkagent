<HTML>
<HEAD>
	<TITLE>JSP Tag Test page</TITLE>
	<%@ page import="javax.servlet.http.HttpUtils,java.util.Enumeration" %>
</HEAD>
<BODY>
<%@ taglib uri="/WEB-INF/lib/tagtest.jar" prefix="test" %>
<test:test1 att1="<h1>eval script(should have value of 2)=<%= (1+1) %></h1><br>" att2="<%= 2+2 %>" >
(this is text between begin and end tag)<br>
</test:test1>
<br>
jsp:useBean test, assign  to '*'<br>
<jsp:useBean id="aaa" scope="page" beanName="BajieSoft.beanTest">
<jsp:setProperty name="aaa" property="*"/>
</jsp:useBean>
<br>This is the current value of strVal:<jsp:getProperty name="aaa" property="strVal"/>
<br>set String property 'strVal' to 'myname', getProperty should return 'myname':<br>
<jsp:setProperty name="aaa" property="strVal" value="myname"/>
get strVal=
<jsp:getProperty name="aaa" property="strVal"/><br>
<br>set char property 'charVal' to 'myname', getProperty should return 'm':
<jsp:setProperty name="aaa" property="charVal" value="myname"/>
<br>get charVal=
<jsp:getProperty name="aaa" property="charVal"/><br>
<br>set int property 'intVal' to '1234', getProperty should return '1234':<br>
<jsp:setProperty name="aaa" property="intVal" value="1234"/>
get intVal=<jsp:getProperty name="aaa" property="intVal"/>
</BODY>
</HTML>