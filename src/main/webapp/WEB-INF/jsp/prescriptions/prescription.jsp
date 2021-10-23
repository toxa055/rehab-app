<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Prescription</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Prescription for ${p.patientName}</h2>
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
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col" style="display: none">Cure type</th>
            <th scope="col" style="display: none">Pattern id</th>
            <th scope="col">Pattern</th>
            <th scope="col" style="display: none">Period id</th>
            <th scope="col">Period</th>
            <th scope="col">Dose</th>
            <th scope="col">Active</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tr class="${p.active ? 'table-warning' : 'table-success'}">
            <td style="display: none">${p.id}</td>
            <td style="display: none">${p.patientId}</td>
            <td>${p.patientInsuranceNumber}</td>
            <td>${p.patientName}</td>
            <td style="display: none">${p.doctorId}</td>
            <td>${p.doctorName}</td>
            <td>${p.date}</td>
            <td style="display: none">${p.cureId}</td>
            <td>${p.cureName}</td>
            <td style="display: none">${p.cureType}</td>
            <td style="display: none">${p.patternId}</td>
            <td>${p.patternCount} times a ${p.patternUnit}
                (<c:forEach items="${p.patternUnits}" var="unit">${unit};
                </c:forEach>)
            </td>
            <td>${p.periodCount} ${p.periodUnit}</td>
            <td>${p.dose}</td>
            <td id="isActive">${p.active}</td>
            <sec:authorize access="hasRole('DOCTOR')">
                <td>
                    <a class="btn btn-outline-danger" href="/prescriptions/cancel/${p.id}"
                       id="prescriptionCancelButtonLink" role="button">Cancel</a>
                    <a class="btn btn-outline-primary" href="/prescriptions/update/${p.id}?treatmentId=${p.treatmentId}"
                       id="prescriptionUpdateButtonLink" role="button">Update</a>
                </td>
            </sec:authorize>
        </tr>
    </table>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>
<script src="${pageContext.request.contextPath}/js/prescription.js"></script>
</body>
</html>
