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
        <h3>Create new employee</h3>
        <form name="f" action="/employees/new" method='POST'>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Name</label>
                    <input type="text" name="name" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Position</label>
                    <input type="text" name="position" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Confirm password</label>
                    <input type="password" name="confirmPassword" class="form-control input-normal"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label class="col-sm-2 col-form-label">Roles:</label><br>
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
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
                    <button type="submit" name="submit" class="btn btn-primary">Create</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
