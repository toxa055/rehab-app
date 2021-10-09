<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Patients</title>
</head>
<body>
<a href="/">Home</a>
<div>
    <p>Patients:</p>
    <c:forEach items="${patients}" var="p">
        <c:out value="${p.id}, ${p.insuranceNumber}, ${p.name}, ${p.address}, ${p.patientState}"/><br/>
    </c:forEach>
</div>
</body>
</html>
