<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatment ${treatment.id}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Treatment ${treatment.id}</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.№</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Doctor id</th>
            <th scope="col">Doctor</th>
            <th scope="col">Date</th>
            <th scope="col">Diagnosis</th>
            <th scope="col">Closed</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="table-light">
            <td style="display: none">${treatment.id}</td>
            <td style="display: none">${treatment.patientId}</td>
            <td>${treatment.patientInsuranceNumber}</td>
            <td>${treatment.patientName}</td>
            <td style="display: none">${treatment.doctorId}</td>
            <td>${treatment.doctorName}</td>
            <td>${treatment.date}</td>
            <td>${treatment.diagnosis}</td>
            <td>${treatment.closed}</td>
            <sec:authorize access="hasRole('DOCTOR')">
                <td><a href="/prescriptions/new/${treatment.id}">
                    <button type="button" class="btn btn-success">New prescription</button>
                </a></td>
            </sec:authorize>
        </tr>
    </table>
    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
