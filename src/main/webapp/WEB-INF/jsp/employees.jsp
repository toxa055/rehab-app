<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Employees</title>
</head>
<body>
<a href="/">Home</a>
<div>
    <p>Employees:</p>
    <c:forEach items="${employees}" var="e">
        <c:out value="${e.id}, ${e.name}, ${e.email}, ${e.position}"/><br/>
    </c:forEach>
</div>
</body>
</html>
