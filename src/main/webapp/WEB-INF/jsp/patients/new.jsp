<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>New patient</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>Create new patient</h3><br>
        <form action="/patients/new" method="post">
            <div class="row">
                <label for="name" class="col-sm-2 col-form-label">Name, Second Name</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="name" id="name" placeholder="Name, Second Name"
                           value="${p.name}" class="form-control ${nameError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="insuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" name="insuranceNumber" id="insuranceNumber" placeholder="insuranceNumber"
                           value="${p.insuranceNumber}"
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
                           value="${p.address}" class="form-control ${addressError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${addressError}
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <a class="btn btn-outline-secondary" href="/patients" role="button">Cancel</a>
                    <button type="submit" name="submit" class="btn btn-outline-primary">Create</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
