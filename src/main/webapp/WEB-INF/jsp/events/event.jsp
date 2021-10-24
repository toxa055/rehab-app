<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Event</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Event for ${event.patientName}</h2>
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
        <tr class="${event.eventState == 'PLANNED' ? 'table-warning' :
        event.eventState == 'PERFORMED' ? 'table-success' : 'table-danger'}">
            <td style="display: none">${event.id}</td>
            <td style="display: none">${event.patientId}</td>
            <td>${event.patientInsuranceNumber}</td>
            <td>${event.patientName}</td>
            <td style="display: none">${event.nurseId}</td>
            <td>${event.nurseName}</td>
            <td style="display: none">${event.prescriptionId}</td>
            <td>${event.plannedDate}</td>
            <td>${event.plannedTime}</td>
            <td>${event.eventState}</td>
            <td style="display: none">${event.cureId}</td>
            <td>${event.cureName}</td>
            <td>${event.cureType}</td>
            <td>${event.dose}</td>
            <td>${event.endDate}</td>
            <td>${event.endTime}</td>
            <td>${event.comment}</td>
        </tr>
    </table>
    <div>
        <sec:authorize access="hasRole('NURSE')">
            <a class="btn btn-outline-success" href="/events/choose/${event.id}" role="button">Choose</a>
            <a class="btn btn-outline-dark" href="/events/discard/${event.id}" role="button">Discard</a>
            <a class="btn btn-outline-success" href="/events/change/${event.id}?state=performed"
               role="button">Perform</a>
            <a class="btn btn-outline-danger" href="/events/change/${event.id}?state=cancelled" role="button">Cancel</a>
        </sec:authorize>
    </div>
    <br>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
