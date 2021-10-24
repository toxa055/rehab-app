<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>New cure</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>Create new cure</h3><br>
        <form action="/cures/new" method="post">
            <div class="row">
                <label for="name" class="col-sm-2 col-form-label">Name</label>
                <div class="form-group col-lg-4 col-form-label">
                    <input type="text" name="name" id="name" placeholder="Name" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <label for="cureType" class="col-sm-2 col-form-label">Type</label>
                <div class="form-group col-lg-4 col-form-label">
                    <select class="form-select" aria-label="Cure" name="cureType" id="cureType">
                        <option value="MEDICINE">MEDICINE</option>
                        <option value="PROCEDURE">PROCEDURE</option>
                    </select>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Cancel
                    </button>
                    <button type="submit" name="submit" class="btn btn-outline-primary">Create</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
