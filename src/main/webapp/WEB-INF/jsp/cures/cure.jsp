<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>${cure.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>${cure.name}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
        </tr>
        </thead>
        <tr class="table-light">
            <td>${cure.id}</td>
            <td>${cure.name}</td>
            <td>${cure.cureType}</td>
        </tr>
    </table>
    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
