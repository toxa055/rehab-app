<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatment ${treatment.id}</title>
</head>
<body>
<jsp:include page="../nav.jsp"/><br>
<div>
    <c:out value="${treatment.id}, ${treatment.patientId}, ${treatment.patientInsuranceNumber},
    ${treatment.patientName}, ${treatment.doctorId}, ${treatment.doctorName},
    ${treatment.date}, ${treatment.diagnosis}, ${treatment.closed}"/><br>
</div>
</body>
</html>
