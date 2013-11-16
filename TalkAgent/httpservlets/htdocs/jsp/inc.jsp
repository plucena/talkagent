<html>
<body>
	<h1>Include test</h1>
Include hello.jsp, tagtest.jsp (with jsp:param of name strVal and value 'the value')
<%@ include file="hello.jsp" %>
<jsp:include page="tagtest.jsp">
<jsp:param name="strVal" value="the value" />
</jsp:include>
<br>finished
</body>
</html>