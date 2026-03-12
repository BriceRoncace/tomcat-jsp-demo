<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@page import="demo.App.Status" %>

<!DOCTYPE html>
<html>
<head>
  <title>Hello JSP</title>
</head>
<body>
<h2>Demo of Nested Enum Access from JSP EL</h2>
<p><em>Nested enum EL resolution fails when JSTL API classes precede tomcat-embed-jasper on classpath</em></p>

<b>demo.App.Status</b> enum values: <br>
<c:forEach var="status" items="${Status.values()}">
  ${status}<br>
</c:forEach>
</body>
</html>