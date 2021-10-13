<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Patient ${patient.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>${patient.name}</h2>
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
        <tr class="table-light">
            <td style="display: none">${patient.id}</td>
            <td>${patient.insuranceNumber}</td>
            <td>${patient.name}</td>
            <td>${patient.address}</td>
            <td>${patient.patientState}</td>
        </tr>
    </table>
    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
