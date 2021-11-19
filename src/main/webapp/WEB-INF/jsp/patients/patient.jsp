<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
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
            <th scope="col">Birth Date</th>
            <th scope="col">Address</th>
            <th scope="col">State</th>
            <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="${patient.patientState == 'TREATING' ? 'table-warning' : 'table-success'}">
            <td style="display: none">${patient.id}</td>
            <td>${patient.insuranceNumber}</td>
            <td>${patient.name}</td>
            <td>${patient.birthDate}</td>
            <td>${patient.address}</td>
            <td id="patientState">${patient.patientState}</td>
            <td>
                <sec:authorize access="hasRole('DOCTOR')">
                    <a class="btn btn-outline-success" href="/treatments/new/${patient.id}" role="button">
                        New Treatment</a>
                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                            data-bs-target="#dischargePatientModal" id="dischargePatientButton">Discharge
                    </button>
                </sec:authorize>
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="btn btn-outline-dark" href="/patients/edit/${patient.id}" role="button">Edit</a>
                </sec:authorize>
            </td>
        </tr>
    </table>
    <div>
        <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
            <a class="btn btn-outline-dark" role="button" target="_blank"
               href="/treatments/filter?insuranceNumber=${patient.insuranceNumber}">All Treatments</a>
            <a class="btn btn-outline-dark" role="button" target="_blank"
               href="/prescriptions/filter?insuranceNumber=${patient.insuranceNumber}">All Prescriptions</a>
            <a class="btn btn-outline-dark" role="button" target="_blank"
               href="/events/filter?insuranceNumber=${patient.insuranceNumber}">All Events</a>
        </sec:authorize>
    </div>
    <br>
    <a class="btn btn-outline-secondary" href="/patients" role="button">Back</a>
</div>
<div class="modal fade" id="dischargePatientModal" tabindex="-1" aria-labelledby="dischargePatientModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="dischargeCancelModalLabel">Do you really want to
                    discharge ${patient.name}?</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <a class="btn btn-outline-primary" href="/patients/discharge/${patient.id}" role="button">Confirm</a>
            </div>
        </div>
    </div>
</div>
<script>
    if ($('#patientState').text() === 'DISCHARGED') {
        $('#dischargePatientButton').attr('class', 'btn btn-outline-danger disabled');
    }
</script>
</body>
</html>
