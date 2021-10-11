<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Prescriptions</title>
</head>
<body>
<jsp:include page="../nav.jsp"/><br>
<div>
    <p>Prescriptions:</p>
    <c:forEach items="${prescriptions}" var="p">
        <c:out value="${p.id}, ${p.patientId}, ${p.patientInsuranceNumber}, ${p.patientName}, ${p.doctorId}, ${p.doctorName},
        ${p.date}, ${p.cureId}, ${p.cureName}, ${p.cureType}, ${p.patternId}, ${p.patternCount}, ${p.patternUnit}, "/>
        <c:forEach items="${p.patternUnits}" var="unit">
            <c:out value="${unit}, "/>
        </c:forEach>
        <c:out value="${p.periodId}, ${p.periodCount}, ${p.periodUnit}, ${p.dose}"/><br>
    </c:forEach>
</div>
<%--<a href="/prescriptions/new">Add new prescription</a>--%>
</body>
</html>
