<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Events</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Events</h2>
    <a href="/events">
        <button type="button" class="btn btn-info">All</button>
    </a>
    <a href="/events/today">
        <button type="button" class="btn btn-info">Today</button>
    </a><br>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.№</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Nurse id</th>
            <th scope="col">Nurse</th>
            <th scope="col" style="display: none">Prescription id</th>
            <th scope="col">Planned Date</th>
            <th scope="col">Planned Time</th>
            <th scope="col">State</th>
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col">Cure type</th>
            <th scope="col">Dose</th>
            <th scope="col">End Date</th>
            <th scope="col">End Time</th>
            <th scope="col">Comment</th>
        </tr>
        </thead>
        <c:forEach items="${events}" var="e">
            <tr class="table-light">
                <td style="display: none">${e.id}</td>
                <td style="display: none">${e.patientId}</td>
                <td>${e.patientInsuranceNumber}</td>
                <td>${e.patientName}</td>
                <td style="display: none">${e.nurseId}</td>
                <td>${e.nurseName}</td>
                <td style="display: none">${e.prescriptionId}</td>
                <td>${e.plannedDate}</td>
                <td>${e.plannedTime}</td>
                <td>${e.eventState}</td>
                <td style="display: none">${e.cureId}</td>
                <td>${e.cureName}</td>
                <td>${e.cureType}</td>
                <td>${e.dose}</td>
                <td>${e.endDate}</td>
                <td>${e.endTime}</td>
                <td>${e.comment}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
