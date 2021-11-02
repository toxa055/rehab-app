<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>New employee</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>Create new employee</h3><br>
        <form name="f" action="/employees/new" method='POST'>
            <div class="row">
                <label for="name" class="col-sm-2 col-form-label">Name, Second Name</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="name" id="name" placeholder="Name, Second name" value="${employee.name}"
                           class="form-control ${nameError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${nameError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="position" class="col-sm-2 col-form-label">Position</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="position" id="position" placeholder="Position" value="${employee.position}"
                           class="form-control ${positionError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${positionError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="email" name="email" id="email" placeholder="Email" value="${employee.email}"
                           class="form-control ${emailError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="password" class="col-sm-2 col-form-label">Password</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="password" name="password" id="password" placeholder="Password"
                           class="form-control ${passwordError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label for="confirmPassword" class="col-sm-2 col-form-label">Confirm password</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm password"
                           class="form-control ${confirmPasswordError != null ? 'is-invalid' : ''}"/>
                    <div class="invalid-feedback">
                        ${confirmPasswordError}
                    </div>
                </div>
            </div>
            <div class="row">
                <label class="col-sm-2 col-form-label">Roles</label>
                <div class="form-group col-lg-4 col-form-label">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" name="roles" value="ADMIN" id="ADMIN">
                        <label class="form-check-label" for="ADMIN">ADMIN</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" name="roles" value="DOCTOR" id="DOCTOR">
                        <label class="form-check-label" for="DOCTOR">DOCTOR</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="checkbox" name="roles" value="NURSE" id="NURSE">
                        <label class="form-check-label" for="NURSE">NURSE</label>
                    </div>
                    <input class="form-control is-invalid" hidden>
                    <div class="invalid-feedback">
                        ${rolesError}
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <a class="btn btn-outline-secondary" href="/employees" role="button">Cancel</a>
                    <button type="submit" name="submit" class="btn btn-outline-primary">Create</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
