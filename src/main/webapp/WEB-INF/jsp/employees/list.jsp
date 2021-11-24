<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Employees</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Employees</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Position</th>
            <th scope="col">Email</th>
            <sec:authorize access="hasRole('ADMIN')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="e">
<%--            <tr class="table-light">--%>
            <tr class="general-grey">
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>${e.position}</td>
                <td>${e.email}</td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td>
                        <a class="btn btn-outline-dark" href="/employees/${e.id}" role="button">Details</a>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    <div class="container" id="pageNav">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:forEach begin="1" end="${page.totalPages}" var="loop">
                    <li class="${loop - 1 == page.number ? 'page-item active' : 'page-item'}">
                        <a class="page-link" href="/employees?page=${loop - 1}">${loop}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
    <sec:authorize access="hasRole('ADMIN')">
        <a class="btn btn-outline-success" href="/employees/new" role="button">New Employee</a>
    </sec:authorize>
</div>
<script>
    let pageCount = ${page.totalPages};
    if (pageCount === 1) {
        $('#pageNav').hide();
    }
</script>
</body>
</html>
