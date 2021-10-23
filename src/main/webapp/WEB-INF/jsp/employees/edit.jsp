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
        <h3>Change your password</h3><br>
        <form action="/employees/edit" method='POST'>
            <div class="row" style="display: none">
                <label class="col-sm-2 col-form-label">id</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="number" name="id" class="form-control" value="${employee.id}" readonly/>
                </div>
            </div>
            <div class="row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="email" name="email" class="form-control" id="email"
                           value="${employee.email}" readonly/>
                </div>
            </div>
            <div class="row">
                <label for="password" class="col-sm-2 col-form-label">New password</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="password" name="password" id="password" placeholder="Confirm" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <label for="confirmPassword" class="col-sm-2 col-form-label">Confirm password</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="password" name="confirmPassword" id="confirmPassword"
                           placeholder="Confirm password" class="form-control input-normal"/>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Cancel</button>
                    <button type="submit" name="submit" class="btn btn-outline-primary">Change</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
