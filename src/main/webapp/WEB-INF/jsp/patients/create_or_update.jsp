<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>${patient.id == null ? 'New patient' : 'Edit patient'}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>${patient.id == null ? 'Create new patient' : 'Edit patient'}</h3><br>
        <form action="/patients/new" method="post">
            <div class="row" style="display: none">
                <label for="id" class="col-sm-2 col-form-label">id</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" name="id" id="id" value="${patient.id}" class="form-control" readonly/>
                </div>
            </div>
            <div class="row">
                <label for="name" class="col-sm-2 col-form-label">Name, Second Name</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="name" id="name" placeholder="Name, Second Name"
                           value="${patient.name}" class="form-control ${nameError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="birthDate" class="col-sm-2 col-form-label">Birth Date</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="date" name="birthDate" id="birthDate" value="${patient.birthDate}"
                           class="form-control ${birthDateError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${birthDateError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="insuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" name="insuranceNumber" id="insuranceNumber" placeholder="insuranceNumber"
                           value="${patient.insuranceNumber}"
                           class="form-control ${insuranceNumberError != null ? 'is-invalid' : ''}">
                    <div class="invalid-feedback">
                        ${insuranceNumberError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="address" class="col-sm-2 col-form-label">Address</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="address" id="address" placeholder="Address"
                           value="${patient.address}" class="form-control ${addressError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${addressError}
                    </div>
                </div>
            </div>
            <div class="row" style="${patient.id == null ? 'display:none' : ''}">
                <label for="patientState" class="col-sm-2 col-form-label">State</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="patientState" id="patientState"
                           value="${patient.id == null ? 'TREATING' : patient.patientState}"
                           class="form-control" readonly/>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <c:choose>
                        <c:when test="${patient.id == null}">
                            <a class="btn btn-outline-secondary" href="/patients" role="button">Cancel</a>
                            <button type="submit" name="submit" class="btn btn-outline-primary">Create</button>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-outline-secondary" href="/patients/${patient.id}"
                               role="button">Cancel</a>
                            <button type="submit" name="submit" class="btn btn-outline-primary">Update</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
