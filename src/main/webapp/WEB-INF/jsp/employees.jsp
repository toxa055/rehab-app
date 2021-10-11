<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Employees</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<br>
<div>
    <h2>Employees:</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Position</th>
            <th scope="col">Email</th>
        </tr>
        </thead>
        <c:forEach items="${employees}" var="e">
            <tr class="table-light">
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>${e.position}</td>
                <td>${e.email}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
