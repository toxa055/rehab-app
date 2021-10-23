<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Patient</title>
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
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="${patient.patientState == 'TREATING' ? 'table-warning' : 'table-success'}">
            <td style="display: none">${patient.id}</td>
            <td>${patient.insuranceNumber}</td>
            <td>${patient.name}</td>
            <td>${patient.address}</td>
            <td id="patientState">${patient.patientState}</td>
            <sec:authorize access="hasRole('DOCTOR')">
                <td>
                    <a class="btn btn-outline-success" href="/treatments/new/${patient.id}" role="button">New Treatment</a>
                    <a class="btn btn-outline-danger" href="/patients/discharge/${patient.id}" role="button"
                       id="dischargeButtonLink">Discharge</a>
                </td>
            </sec:authorize>
        </tr>
    </table>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>
<script language="javascript">
    if ($('#patientState').text() === 'DISCHARGED') {
        $('#dischargeButtonLink').attr('class', 'btn btn-outline-danger disabled');
    }
</script>
</body>
</html>
