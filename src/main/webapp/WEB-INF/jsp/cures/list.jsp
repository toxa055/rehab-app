<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Cures</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Cures</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="cure">
            <tr class="general-grey">
                <td>${cure.id}</td>
                <td>${cure.name}</td>
                <td>${cure.cureType}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="container" id="pageNav">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:forEach begin="1" end="${page.totalPages}" var="loop">
                    <li class="${loop - 1 == page.number ? 'page-item active' : 'page-item'}">
                        <a class="page-link" href="/cures?page=${loop - 1}">${loop}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
    <a class="btn btn-outline-success" href="/cures/new" role="button">New Cure</a>
</div>
<script>
    let pageCount = ${page.totalPages};
    if (pageCount === 1) {
        $('#pageNav').hide();
    }
</script>
</body>
</html>
