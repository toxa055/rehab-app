<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Cures</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div>
    <h2>Cures:</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
        </tr>
        </thead>
        <c:forEach items="${cures}" var="cure">
            <tr class="table-light">
                <td>${cure.id}</td>
                <td>${cure.name}</td>
                <td>${cure.cureType}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<a href="/cures/new">Add new cure</a>
</body>
</html>
