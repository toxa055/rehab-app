<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Event ${event.id}</title>
</head>
<body>
<a href="../">Home</a><br>
<a href="/events">Event</a>
<div>
    <c:out value="${event.id}, ${event.patientId}, ${event.patientInsuranceNumber}, ${event.patientName}, ${event.nurseId},
    ${event.nurseName}, ${event.plannedDate}, ${event.plannedTime}, ${event.eventState}, ${event.cureId}, ${event.cureName},
    ${event.cureType}, ${event.dose}, ${event.endDate}, ${event.endTime}, ${event.comment}"/><br>
</div>
</body>
</html>
