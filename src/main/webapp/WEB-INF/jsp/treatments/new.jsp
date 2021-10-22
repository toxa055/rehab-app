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
    <div class="container">
        <h3>Create new treatment</h3><br>
        <form action="/treatments/new/" method="post">
            <div class="row" style="display: none">
                <label for="patientId" class="col-sm-2 col-form-label">Patient id</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" class="form-control" name="patientId"
                           id="patientId" value="${patient.id}" readonly>
                </div>
            </div>
            <div class="row">
                <label for="patientInsuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" class="form-control" name="patientInsuranceNumber" id="patientInsuranceNumber"
                           placeholder="Insurance number" value="${patient.insuranceNumber}" readonly><br>
                    <input type="button" class="btn btn-primary" id="searchByInsNum" value="Search">
                    <input type="button" class="btn btn-primary" id="changeInsNum" value="Change" disabled>
                </div>
            </div>
            <div class="row">
                <label for="patientName" class="col-sm-2 col-form-label">Name, Second Name</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" class="form-control" name="patientName" id="patientName"
                           placeholder="Name, Second Name" value="${patient.name}" readonly>
                </div>
            </div>
            <div class="row" style="display: none">
                <label for="address" class="col-sm-2 col-form-label">Address</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" class="form-control" name="address" id="address"
                           value="${patient.address}" readonly>
                </div>
            </div>
            <div class="row">
                <label for="diagnosis" class="col-sm-2 col-form-label">Diagnosis</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" class="form-control" name="diagnosis" id="diagnosis" placeholder="Diagnosis">
                </div>
            </div>
            <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
            <button type="submit" class="btn btn-primary" id="saveButton" disabled>Save</button>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/find_patient.js"></script>
</body>
</html>
