<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>New treatment</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>New treatment</h2>
    <br>
    <form action="/treatments/new/" method="post">
        <div class="row mb-3" style="display: none">
            <label for="patientId" class="col-sm-2 col-form-label">Patient id</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="patientId"
                       id="patientId" value="${patient.id}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="patientInsuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="patientInsuranceNumber"
                       id="patientInsuranceNumber" value="${patient.insuranceNumber}" readonly><br>
                <input type="button" class="btn btn-primary" id="searchByInsNum" value="Search">
                <input type="button" class="btn btn-primary" id="changeInsNum" value="Change" disabled>
            </div>
        </div>
        <div class="row mb-3">
            <label for="patientName" class="col-sm-2 col-form-label">Name, Second Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="patientName" id="patientName"
                       value="${patient.name}" readonly>
            </div>
        </div>
        <div class="row mb-3" style="display: none">
            <label for="address" class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="address" id="address" value="${patient.address}" readonly>
            </div>
        </div>
        <div class="row mb-3">
            <label for="diagnosis" class="col-sm-2 col-form-label">Diagnosis</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="diagnosis" id="diagnosis" placeholder="Diagnosis">
            </div>
        </div>
        <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
        <button type="submit" class="btn btn-primary" id="saveButton" disabled>Save</button>
    </form>
</div>
<script src="${pageContext.request.contextPath}/js/find_patient.js"></script>
</body>
</html>
