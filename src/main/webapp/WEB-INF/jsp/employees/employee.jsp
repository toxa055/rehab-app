<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>${employee.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
    <h2>${employee.name}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Position</th>
            <th scope="col">Email</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tr class="general-grey">
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.position}</td>
            <td>${employee.email}</td>
            <td>
                <c:choose>
                    <c:when test="${employee.id == authId}">
                        <a class="btn btn-outline-dark" href="/employees/profile/edit" role="button">
                            Change Password</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-outline-dark" href="/employees/${employee.id}/edit" role="button">
                            Change Password</a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>
<div id="main"></div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
