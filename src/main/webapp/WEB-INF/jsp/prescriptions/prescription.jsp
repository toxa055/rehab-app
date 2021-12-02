<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title>Prescription</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
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
            <th scope="col" style="display: none">Type</th>
            <th scope="col" style="display: none">Pattern id</th>
            <th scope="col">Pattern</th>
            <th scope="col" style="display: none">Period id</th>
            <th scope="col">Period</th>
            <th scope="col">Dose</th>
            <th scope="col">Active</th>
        </tr>
        </thead>
        <tr class="${p.active ? 'active-yellow' : 'active-green'}">
            <td style="display: none">${p.id}</td>
            <td style="display: none">${p.patientId}</td>
            <td>${p.patientInsuranceNumber}</td>
            <td>${p.patientName}</td>
            <td id="doctorId" style="display: none">${p.doctorId}</td>
            <td>${p.doctorName}</td>
            <td>${p.date}</td>
            <td style="display: none">${p.cureId}</td>
            <td>${p.cureName}</td>
            <td style="display: none">${p.cureType}</td>
            <td style="display: none">${p.patternId}</td>
            <td>${p.patternCount} times a ${p.patternUnit} (${p.patternUnits})</td>
            <td>${p.periodCount} ${p.periodUnit}</td>
            <td>${p.dose}</td>
            <td id="isActive">${p.active}</td>
        </tr>
    </table>
    <div>
        <sec:authorize access="hasRole('DOCTOR')">
            <button type="button" class="btn btn-outline-success" data-bs-toggle="modal"
                    data-bs-target="#prescriptionCloseModal" id="prescriptionCloseButton">Close
            </button>
            <button type="button" class="btn btn-outline-primary" data-bs-toggle="modal"
                    data-bs-target="#prescriptionUpdateModal" id="prescriptionUpdateButton">Update
            </button>
            <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal"
                    data-bs-target="#prescriptionCancelModal" id="prescriptionCancelButton">Cancel
            </button>
        </sec:authorize>
        <a class="btn btn-outline-dark" role="button" target="_blank" href="/events/prescription/${p.id}">
            Current Events</a>
    </div>
    <br>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>

<div class="modal fade" id="prescriptionCloseModal" tabindex="-1" aria-labelledby="prescriptionCloseModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="prescriptionCloseModalLabel">Do you really want to close the
                    prescription?</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h5>
                    The prescription will be closed.<br>
                    Before closing, there should be no one planned events relating to the prescription.
                </h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <a class="btn btn-outline-primary" href="/prescriptions/close/${p.id}" role="button">Confirm</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="prescriptionCancelModal" tabindex="-1" aria-labelledby="prescriptionCancelModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="prescriptionCancelModalLabel">Do you really want to cancel the
                    prescription?</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h5>
                    The prescription will be cancelled.<br>
                    All planned events relating to the prescription will be cancelled.
                </h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <a class="btn btn-outline-primary" href="/prescriptions/cancel/${p.id}" role="button">Confirm</a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="prescriptionUpdateModal" tabindex="-1" aria-labelledby="prescriptionUpdateModal"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="prescriptionUpdateModalLabel">Do you really want to update the
                    prescription?</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h5>
                    The prescription will be cancelled.<br>
                    All planned events relating to the prescription will be cancelled.<br>
                    New prescription will be created.
                </h5>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <a class="btn btn-outline-primary" href="/prescriptions/edit/${p.treatmentId}/${p.id}"
                   role="button">Confirm</a>
            </div>
        </div>
    </div>
</div>

<div id="main"></div>
<jsp:include page="../footer.jsp"/>
<script>
    if ($('#doctorId').text() != ${authDoctorId}) {
        $('#prescriptionCloseButton').attr('disabled', 'true');
        $('#prescriptionCancelButton').attr('disabled', 'true');
        $('#prescriptionUpdateButton').attr('disabled', 'true');
    } else {
        $('#prescriptionCloseButton').attr('disabled', false);
        $('#prescriptionCancelButton').attr('disabled', false);
        $('#prescriptionUpdateButton').attr('disabled', false);
    }
    if ($('#isActive').text() === 'false') {
        $('#prescriptionCloseButton').attr('disabled', 'true');
        $('#prescriptionCancelButton').attr('disabled', 'true');
        $('#prescriptionUpdateButton').attr('disabled', 'true');
    }
</script>
</body>
</html>
