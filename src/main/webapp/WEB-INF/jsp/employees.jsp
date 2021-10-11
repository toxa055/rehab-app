<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Employees</title>
</head>
<body>
<jsp:include page="nav.jsp"/><br>
<div>
    <p>Employees:</p>
    <c:forEach items="${employees}" var="e">
        <c:out value="${e.id}, ${e.name}, ${e.email}, ${e.position}"/><br/>
    </c:forEach>
</div>
</body>
</html>
