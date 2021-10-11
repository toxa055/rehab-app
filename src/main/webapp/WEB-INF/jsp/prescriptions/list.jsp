<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Prescriptions</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div>
    <h2>Prescriptions:</h2>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.№</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Doctor id</th>
            <th scope="col">Doctor</th>
            <th scope="col">Date</th>
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col" style="display: none">Cure type</th>
            <th scope="col" style="display: none">Pattern id</th>
            <th scope="col">Pattern</th>
            <th scope="col" style="display: none">Period id</th>
            <th scope="col">Period</th>
            <th scope="col">Dose</th>
        </tr>
        </thead>
        <c:forEach items="${prescriptions}" var="p">
            <tr class="table-light">
                <td style="display: none">${p.id}</td>
                <td style="display: none">${p.patientId}</td>
                <td>${p.patientInsuranceNumber}</td>
                <td>${p.patientName}</td>
                <td style="display: none">${p.doctorId}</td>
                <td>${p.doctorName}</td>
                <td>${p.date}</td>
                <td style="display: none">${p.cureId}</td>
                <td>${p.cureName}</td>
                <td style="display: none">${p.cureType}</td>
                <td style="display: none">${p.patternId}</td>
                <td>${p.patternCount} times a ${p.patternUnit}
                    (<c:forEach items="${p.patternUnits}" var="unit">${unit};
                    </c:forEach>)
                </td>
                <td>${p.periodCount} ${p.periodUnit}</td>
                <td>${p.dose}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<%--<a href="/prescriptions/new">Add new prescription</a>--%>
</body>
</html>
