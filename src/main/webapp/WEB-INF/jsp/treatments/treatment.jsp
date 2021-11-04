<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatment</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>${treatment.patientName}'s treatment</h2>
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
            <th scope="col">Close date</th>
            <th scope="col">Closed</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="${treatment.closed ? 'table-success' : 'table-warning'}">
            <td style="display: none">${treatment.id}</td>
            <td style="display: none">${treatment.patientId}</td>
            <td>${treatment.patientInsuranceNumber}</td>
            <td>${treatment.patientName}</td>
            <td id="doctorId" style="display: none">${treatment.doctorId}</td>
            <td>${treatment.doctorName}</td>
            <td>${treatment.date}</td>
            <td>${treatment.diagnosis}</td>
            <td>${treatment.closeDate}</td>
            <td>${treatment.closed}</td>
            <sec:authorize access="hasRole('DOCTOR')">
                <td>
                    <a class="btn btn-outline-success" href="/prescriptions/new/${treatment.id}"
                       id="newPrescriptionButtonLink" role="button">New Prescription</a>
                    <a class="btn btn-outline-danger" href="/treatments/close/${treatment.id}"
                       id="closeTreatmentButtonLink" role="button">Close</a>
                </td>
            </sec:authorize>
        </tr>
    </table>
    <div>
        <a class="btn btn-outline-dark" role="button" target="_blank" href="/prescriptions/treatment/${treatment.id}">
            Current Prescriptions</a>
    </div>
    <br>
    <a class="btn btn-outline-secondary" href="/treatments/today" role="button">Back</a>
</div>
<script>
    if ($('#doctorId').text() != ${authDoctorId}) {
        $('#newPrescriptionButtonLink').attr('class', 'btn btn-outline-success disabled');
        $('#closeTreatmentButtonLink').attr('class', 'btn btn-outline-danger disabled');
    } else {
        $('#newPrescriptionButtonLink').attr('class', 'btn btn-outline-success');
        $('#closeTreatmentButtonLink').attr('class', 'btn btn-outline-danger');
    }
    if (${treatment.closed}) {
        $('#newPrescriptionButtonLink').attr('class', 'btn btn-outline-success disabled');
        $('#closeTreatmentButtonLink').attr('class', 'btn btn-outline-danger disabled');
    }
</script>
</body>
</html>
