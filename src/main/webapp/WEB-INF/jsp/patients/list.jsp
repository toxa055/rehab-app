<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Patients</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Patients</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col">Insurance №</th>
            <th scope="col">Name</th>
            <th scope="col">Address</th>
            <th scope="col">State</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${patients}" var="p">
            <tr class="table-light">
                <td style="display: none">${p.id}</td>
                <td>${p.insuranceNumber}</td>
                <td>${p.name}</td>
                <td>${p.address}</td>
                <td>${p.patientState}</td>
                <sec:authorize access="hasRole('DOCTOR')">
                    <td><a href="../treatments/new/${p.id}">
                        <button type="button" class="btn btn-success">New treatment</button>
                    </a></td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    <sec:authorize access="hasRole('DOCTOR')">
        <a href="/patients/new">
            <button type="button" class="btn btn-success">Add new</button>
        </a>
    </sec:authorize>
</div>
</body>
</html>
