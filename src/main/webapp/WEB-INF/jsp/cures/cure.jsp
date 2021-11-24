<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>${cure.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
    <h2>${cure.name}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
        </tr>
        </thead>
        <tr class="general-grey">
            <td>${cure.id}</td>
            <td>${cure.name}</td>
            <td>${cure.cureType}</td>
        </tr>
    </table>
    <a class="btn btn-outline-secondary" href="/cures" role="button">Back</a>
</div>
<div id="main"></div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
