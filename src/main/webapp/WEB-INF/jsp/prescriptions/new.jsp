<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>New prescription</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>New prescription</h2>
    <br>
    <form action="/prescriptions/new/" method="post">
        <div class="row mb-3" style="display: none">
            <label for="treatmentId" class="col-sm-2 col-form-label">Treatment id</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="treatmentId" id="treatmentId"
                       value="${treatment.id}" readonly>
            </div>
        </div>
        <div class="row mb-3" style="display: none">
            <label for="patientId" class="col-sm-2 col-form-label">Patient id</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="patientId" id="patientId"
                       value="${treatment.patientId}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="patientInsuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="patientInsuranceNumber"
                       id="patientInsuranceNumber" value="${treatment.patientInsuranceNumber}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="patientName" class="col-sm-2 col-form-label">Patient</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="patientName" id="patientName"
                       value="${treatment.patientName}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="diagnosis" class="col-sm-2 col-form-label">Diagnosis</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="diagnosis" id="diagnosis"
                       value="${treatment.diagnosis}" readonly>
            </div>
        </div>
        <div class="row mb-3" style="display: none">
            <label for="doctorId" class="col-sm-2 col-form-label">Doctor id</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="doctorId" id="doctorId"
                       value="${treatment.doctorId}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="doctorName" class="col-sm-2 col-form-label">Doctor</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="doctorName" id="doctorName"
                       value="${treatment.doctorName}" readonly>
            </div>
        </div>
        <div class="row mb-3" style="display: none">
            <label for="date" class="col-sm-2 col-form-label">Date</label>
            <div class="col-sm-10">
                <input type="date" class="form-control" name="date" id="date" value="${treatment.date}" readonly>
            </div>
        </div>
        <div class="row mb-3" style="display: none">
            <label for="cureId" class="col-sm-2 col-form-label">Cure id</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="cureId" id="cureId" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cureType" class="col-sm-2 col-form-label">CureType</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="cureType" id="cureType" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="cureName" class="col-sm-2 col-form-label">Cure</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="cureName" id="cureName" placeholder="Cure"><br>
                <input type="button" class="btn btn-primary" id="searchByCureName" value="Search">
                <input type="button" class="btn btn-primary" id="changeCure" value="Change" disabled>
            </div>
        </div>
        <div class="row mb-3">
            <label for="dose" class="col-sm-2 col-form-label">Dose</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="dose" id="dose" placeholder="Dose">
            </div>
        </div>
        <div class="row mb-3">
            <label for="patternCount" class="col-sm-2 col-form-label">Count</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Count" name="patternCount" id="patternCount">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                </select>
            </div>
        </div>
        <div class="row mb-3">
            <label for="patternUnit" class="col-sm-2 col-form-label">Unit</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Unit" name="patternUnit" id="patternUnit">
                    <option>DAY</option>
                    <option>WEEK</option>
                </select>
            </div>
        </div>
        <div class="parts-of-day">
            <label ????? class="col-sm-2 col-form-label">Units</label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="MORNING" id="MORNING">
                <label class="form-check-label" for="MORNING">MORNING</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="AFTERNOON" id="AFTERNOON">
                <label class="form-check-label" for="AFTERNOON">AFTERNOON</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="EVENING" id="EVENING">
                <label class="form-check-label" for="EVENING">EVENING</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="NIGHT" id="NIGHT">
                <label class="form-check-label" for="NIGHT">NIGHT</label>
            </div>
        </div>
        <div class="days-of-week" hidden>
            <label ????? class="col-sm-2 col-form-label">Units</label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="MONDAY" id="MONDAY">
                <label class="form-check-label" for="MONDAY">MONDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="TUESDAY" id="TUESDAY">
                <label class="form-check-label" for="TUESDAY">TUESDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="WEDNESDAY" id="WEDNESDAY">
                <label class="form-check-label" for="WEDNESDAY">WEDNESDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="THURSDAY" id="THURSDAY">
                <label class="form-check-label" for="THURSDAY">THURSDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="FRIDAY" id="FRIDAY">
                <label class="form-check-label" for="FRIDAY">FRIDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="SATURDAY" id="SATURDAY">
                <label class="form-check-label" for="SATURDAY">SATURDAY</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="patternUnits" value="SUNDAY" id="SUNDAY">
                <label class="form-check-label" for="SUNDAY">SUNDAY</label>
            </div>
        </div>
        <br>
        <div class="row mb-3">
            <label for="periodCount" class="col-sm-2 col-form-label">Count</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="periodCount" id="periodCount"
                       placeholder="Count" min="1" max="300">
            </div>
        </div>
        <div class="row mb-3">
            <label for="periodUnit" class="col-sm-2 col-form-label">Unit</label>
            <div class="col-sm-10">
                <select class="form-select" aria-label="Unit" name="periodUnit" id="periodUnit">
                    <option value="DAY">DAY</option>
                    <option value="WEEK">WEEK</option>
                    <option value="MONTH">MONTH</option>
                </select>
            </div>
        </div>
        <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
        <button type="submit" class="btn btn-primary" id="saveButton">Save</button>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/find_cure.js"></script>
</body>
</html>
