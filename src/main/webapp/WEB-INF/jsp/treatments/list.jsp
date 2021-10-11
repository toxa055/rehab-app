<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatments</title>
</head>
<body>
<jsp:include page="../nav.jsp"/><br>
<div>
    <p>Treatments:</p>
    <c:forEach items="${treatments}" var="t">
        <c:out value="${t.id}, ${t.patientId}, ${t.patientInsuranceNumber}, ${t.patientName},
        ${t.doctorId}, ${t.doctorName}, ${t.date}, ${t.diagnosis}, ${t.closed}"/><br>
    </c:forEach><br>
</div>
<%--<a href="/treatments/new">Add new treatment</a>--%>
</body>
</html>
