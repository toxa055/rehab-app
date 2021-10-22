<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>${employee.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>${employee.name}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Position</th>
            <th scope="col">Email</th>
            <sec:authorize access="isAuthenticated()">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="table-light">
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.position}</td>
            <td>${employee.email}</td>
            <sec:authorize access="isAuthenticated()">
                <td>
                    <a href="/employees/edit">
                        <button type="button" class="btn btn-warning">Change Password</button>
                    </a>
                </td>
            </sec:authorize>
        </tr>
    </table>
    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
