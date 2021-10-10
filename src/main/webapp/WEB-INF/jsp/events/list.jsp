<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Events</title>
</head>
<body>
<a href="../">Home</a>
<div>
    <p>Events:</p>
    <c:forEach items="${events}" var="e">
        <c:out value="${e.id}, ${e.patientId}, ${e.patientInsuranceNumber}, ${e.patientName}, ${e.nurseId},
        ${e.nurseName}, ${e.plannedDate}, ${e.plannedTime}, ${e.eventState}, ${e.cureId}, ${e.cureName},
        ${e.cureType}, ${e.dose}, ${e.endDate}, ${e.endTime}, ${e.comment}"/><br>
    </c:forEach><br>
</div>
</body>
</html>
