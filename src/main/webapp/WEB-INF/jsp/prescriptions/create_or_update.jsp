<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <title>${p.id == null ? 'New' : 'Update'} prescription</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>${p.id == null ? 'Create new' : 'Update'} prescription for ${treatment.patientName}</h3><br>
        <form action="/prescriptions/new" method="post" id="f">
            <div class="row">
                <div class="form-group col-lg-6">
                    <div class="row" style="display: none">
                        <label for="id" class="col-sm-4 col-form-label">Prescription id</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" name="id" id="id" value="${p.id}" class="form-control" readonly>
                        </div>
                    </div>
                    <div class="row" style="display: none">
                        <label for="treatmentId" class="col-sm-4 col-form-label">Treatment id</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" name="treatmentId" id="treatmentId" value="${treatment.id}"
                                   class="form-control" readonly>
                        </div>
                    </div>
                    <div class="row" style="display: none">
                        <label for="patientId" class="col-sm-4 col-form-label">Patient id</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" name="patientId" id="patientId" value="${treatment.patientId}"
                                   class="form-control" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="patientInsuranceNumber" class="col-sm-4 col-form-label">Insurance number</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" class="form-control" name="patientInsuranceNumber"
                                   id="patientInsuranceNumber" value="${treatment.patientInsuranceNumber}" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="patientName" class="col-sm-4 col-form-label">Patient</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control" name="patientName" id="patientName"
                                   value="${treatment.patientName}" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="diagnosis" class="col-sm-4 col-form-label">Diagnosis</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control" name="diagnosis" id="diagnosis"
                                   value="${treatment.diagnosis}" readonly>
                        </div>
                    </div>
                    <div class="row" style="display: none">
                        <label for="doctorId" class="col-sm-4 col-form-label">Doctor id</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" class="form-control" name="doctorId" id="doctorId"
                                   value="${treatment.doctorId}" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="doctorName" class="col-sm-4 col-form-label">Doctor</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control" name="doctorName" id="doctorName"
                                   value="${treatment.doctorName}" readonly>
                        </div>
                    </div>
                    <div class="row" style="display: none">
                        <label for="cureId" class="col-sm-4 col-form-label">Cure id</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" class="form-control" name="cureId" id="cureId"
                                   value="${p.cureId}" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="cureType" class="col-sm-4 col-form-label">Cure type</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control" name="cureType" id="cureType"
                                   value="${p.cureType}" readonly>
                        </div>
                    </div>
                    <div class="row">
                        <label for="cureName" class="col-sm-4 col-form-label">Cure</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control ${cureNameError != null ? 'is-invalid' : ''}"
                                   value="${p.cureName}" name="cureName" id="cureName" placeholder="Cure">
                            <div class="invalid-feedback" id="invalidCureName">
                                ${cureNameError}
                            </div>
                            <br>
                            <input type="button" class="btn btn-outline-primary" id="searchByCureName" value="Search">
                            <input type="button" class="btn btn-outline-primary" id="changeCure" value="Change"
                                   disabled>
                            <button type="button" class="btn btn-outline-success" data-bs-toggle="modal"
                                    data-bs-target="#newCureModal" id="newCureModalButton">Create
                            </button>
                        </div>
                    </div>
                    <div class="row">
                        <label for="dose" class="col-sm-4 col-form-label">Dose</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="text" class="form-control ${doseError != null ? 'is-invalid' : ''}"
                                   value="${p.dose}" name="dose" id="dose" placeholder="Dose">
                            <div class="invalid-feedback">
                                ${doseError}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group col-lg-6">
                    <div class="row">
                        <label for="patternCount" class="col-sm-4 col-form-label">Count</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <select class="form-select" aria-label="Count" name="patternCount" id="patternCount">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <label for="patternUnit" class="col-sm-4 col-form-label"></label>
                        <div class="form-group col-lg-7 col-form-label">
                            <select class="form-select" aria-label="Unit" name="patternUnit" id="patternUnit">
                                <option>DAY</option>
                                <option>WEEK</option>
                            </select>
                        </div>
                    </div>
                    <div class="row" id="parts-of-day">
                        <label class="col-sm-4 col-form-label">Times of day</label>
                        <div class="form-group col-lg-4 col-form-label">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="MORNING"
                                       id="MORNING">
                                <label class="form-check-label" for="MORNING">MORNING</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="AFTERNOON"
                                       id="AFTERNOON">
                                <label class="form-check-label" for="AFTERNOON">AFTERNOON</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="EVENING"
                                       id="EVENING">
                                <label class="form-check-label" for="EVENING">EVENING</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="NIGHT"
                                       id="NIGHT">
                                <label class="form-check-label" for="NIGHT">NIGHT</label>
                            </div>
                            <input class="form-control is-invalid" hidden>
                            <div class="invalid-feedback">
                                ${patternUnitsError}
                            </div>
                        </div>
                    </div>
                    <div class="row" id="days-of-week" hidden>
                        <label class="col-sm-4 col-form-label">Days of week</label>
                        <div class="form-group col-lg-4 col-form-label">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="MONDAY"
                                       id="MONDAY">
                                <label class="form-check-label" for="MONDAY">MONDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="TUESDAY"
                                       id="TUESDAY">
                                <label class="form-check-label" for="TUESDAY">TUESDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="WEDNESDAY"
                                       id="WEDNESDAY">
                                <label class="form-check-label" for="WEDNESDAY">WEDNESDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="THURSDAY"
                                       id="THURSDAY">
                                <label class="form-check-label" for="THURSDAY">THURSDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="FRIDAY"
                                       id="FRIDAY">
                                <label class="form-check-label" for="FRIDAY">FRIDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="SATURDAY"
                                       id="SATURDAY">
                                <label class="form-check-label" for="SATURDAY">SATURDAY</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" name="patternUnits" value="SUNDAY"
                                       id="SUNDAY">
                                <label class="form-check-label" for="SUNDAY">SUNDAY</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <label for="periodCount" class="col-sm-4 col-form-label">Period</label>
                        <div class="form-group col-lg-7 col-form-label">
                            <input type="number" class="form-control ${periodCountError != null ? 'is-invalid' : ''}"
                                   value="${p.periodCount}" name="periodCount" id="periodCount" placeholder="Count">
                            <div class="invalid-feedback">
                                ${periodCountError}
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <label for="periodUnit" class="col-sm-4 col-form-label"></label>
                        <div class="form-group col-lg-7 col-form-label">
                            <select class="form-select" aria-label="Unit" name="periodUnit" id="periodUnit">
                                <option value="DAY">DAY</option>
                                <option value="WEEK" ${p.periodUnit == 'WEEK' ? 'selected' : ''}>WEEK</option>
                                <option value="MONTH" ${p.periodUnit == 'MONTH' ? 'selected' : ''}>MONTH</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <a class="btn btn-outline-secondary" href="/treatments/${treatment.id}" role="button">Cancel</a>
                    <button type="submit" name="submit" id="sendFormButton" class="btn btn-outline-primary" disabled>
                        ${p.id == null ? 'Create' : 'Update'}
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal fade" id="newCureModal" tabindex="-1" aria-labelledby="newCureModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="newCureModalLabel">Create new cure</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="name" class="col-form-label">Name</label>
                        <input type="text" class="form-control" name="name" id="name">
                        <div class="invalid-feedback" id="invalidCureNameModal"></div>
                    </div>
                    <div class="mb-3">
                        <label for="cureTypeModal" class="col-form-label">Type</label>
                        <div class="form-group col-auto col-form-label">
                            <select class="form-select" aria-label="Cure" name="cureTypeModal" id="cureTypeModal">
                                <option value="MEDICINE">MEDICINE</option>
                                <option value="PROCEDURE">PROCEDURE</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="submit" name="submit" id="cureCreateButton" class="btn btn-outline-success">Create
                </button>
            </div>
        </div>
    </div>
</div>
<script>
    if (${p.id != null}) {
        $('#f').attr('action', '/prescriptions/edit');
    }
</script>
<script src="${pageContext.request.contextPath}/js/new_cure.js"></script>
<script src="${pageContext.request.contextPath}/js/find_cure.js"></script>
</body>
</html>
