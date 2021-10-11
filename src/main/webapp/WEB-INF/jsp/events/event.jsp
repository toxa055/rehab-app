<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Event ${event.id}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/><br>
<div>
    <c:out value="${event.id}, ${event.patientId}, ${event.patientInsuranceNumber}, ${event.patientName}, ${event.nurseId},
    ${event.nurseName}, ${event.plannedDate}, ${event.plannedTime}, ${event.eventState}, ${event.cureId}, ${event.cureName},
    ${event.cureType}, ${event.dose}, ${event.endDate}, ${event.endTime}, ${event.comment}"/><br>
</div>
</body>
</html>
