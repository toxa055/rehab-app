<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatments</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Treatments</h2>
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
        </tr>
        </thead>
        <c:forEach items="${treatments}" var="t">
            <tr class="table-light">
                <td style="display: none">${t.id}</td>
                <td style="display: none">${t.patientId}</td>
                <td>${t.patientInsuranceNumber}</td>
                <td>${t.patientName}</td>
                <td style="display: none">${t.doctorId}</td>
                <td>${t.doctorName}</td>
                <td>${t.date}</td>
                <td>${t.diagnosis}</td>
                <td>${t.closed}</td>
            </tr>
        </c:forEach>
    </table>
    <sec:authorize access="hasRole('DOCTOR')">
        <a href="/treatments/new">
            <button type="button" class="btn btn-success">Add new</button>
        </a>
    </sec:authorize>
</div>
</body>
</html>
