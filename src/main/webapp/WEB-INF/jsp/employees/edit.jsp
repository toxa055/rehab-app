<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>${employee.name}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>Change your password</h3>
        <form name="f" action="/employees/edit" method='POST'>
            <div class="row" style="display: none">
                <div class="form-group col-lg-4 col-form-label">
                    <label>id</label>
                    <input type="number" name="id" class="form-control" value="${employee.id}"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" value="${employee.email}" readonly/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>New password</label>
                    <input type="password" name="password" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4 col-form-label">
                    <label>Confirm password</label>
                    <input type="password" name="confirmPassword" class="form-control input-normal"/>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
                    <button type="submit" name="submit" class="btn btn-primary">Change</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
