<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Add new patient</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Add new patient</h2>
    <br>
    <form action="/patients/new" method="post">
        <div class="row mb-3">
            <label for="insuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="insuranceNumber"
                       id="insuranceNumber" placeholder="Insurance number">
            </div>
        </div>
        <div class="row mb-3">
            <label for="name" class="col-sm-2 col-form-label">Name, Second Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="name" id="name" placeholder="Name, Second Name">
            </div>
        </div>
        <div class="row mb-3">
            <label for="address" class="col-sm-2 col-form-label">Address</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="address" id="address" placeholder="Address">
            </div>
        </div>
        <button type="reset" class="btn btn-secondary" onclick="window.history.back()">Cancel</button>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</div>
</body>
</html>
