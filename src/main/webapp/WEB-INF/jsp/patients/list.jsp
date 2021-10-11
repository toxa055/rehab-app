<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Patients</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div>
    <h2>Patients:</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col">Insurance №</th>
            <th scope="col">Name</th>
            <th scope="col">Address</th>
            <th scope="col">State</th>
        </tr>
        </thead>
        <c:forEach items="${patients}" var="p">
            <tr class="table-light">
                <td style="display: none">${p.id}</td>
                <td>${p.insuranceNumber}</td>
                <td>${p.name}</td>
                <td>${p.address}</td>
                <td>${p.patientState}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<a href="/patients/new">Add new patient</a>
</body>
</html>
