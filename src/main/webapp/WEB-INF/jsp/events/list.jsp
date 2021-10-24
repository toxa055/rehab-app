<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Events</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Events</h2>
    <div>
        <form action="/events/filter" method="get">
            <div class="row mb-3">
                <label for="plannedDate" class="col-sm-2 col-form-label">Date</label>
                <div class="col-auto">
                    <input type="date" class="form-control" name="plannedDate" id="plannedDate">
                </div>
            </div>
            <div class="row mb-3">
                <label for="insuranceNumber" class="col-sm-2 col-form-label">Insurance number</label>
                <div class="col-auto">
                    <input type="number" class="form-control" name="insuranceNumber" id="insuranceNumber" min="1">
                </div>
            </div>
            <button type="submit" class="btn btn-outline-dark">Filter</button>
        </form>
        <div>
            <a class="btn btn-outline-dark" href="/events" role="button">All Events</a>
            <a class="btn btn-outline-dark" href="/events/today" role="button">Today Events</a>
        </div>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.№</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Nurse id</th>
            <th scope="col">Nurse</th>
            <th scope="col" style="display: none">Prescription id</th>
            <th scope="col">Planned Date</th>
            <th scope="col">Planned Time</th>
            <th scope="col">State</th>
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col">Cure type</th>
            <th scope="col">Dose</th>
            <th scope="col">End Date / Time</th>
            <th scope="col">Comment</th>
            <sec:authorize access="hasRole('NURSE')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="e">
            <tr class="${e.eventState == 'PLANNED' ? 'table-warning' :
             e.eventState == 'PERFORMED' ? 'table-success' : 'table-danger'}">
                <td style="display: none">${e.id}</td>
                <td style="display: none">${e.patientId}</td>
                <td>${e.patientInsuranceNumber}</td>
                <td>${e.patientName}</td>
                <td style="display: none">${e.nurseId}</td>
                <td>${e.nurseName}</td>
                <td style="display: none">${e.prescriptionId}</td>
                <td>${e.plannedDate}</td>
                <td>${e.plannedTime}</td>
                <td>${e.eventState}</td>
                <td style="display: none">${e.cureId}</td>
                <td>${e.cureName}</td>
                <td>${e.cureType}</td>
                <td>${e.dose}</td>
                <td>${e.endDate} ${e.endTime}</td>
                <td>${e.comment}</td>
                <sec:authorize access="hasRole('NURSE')">
                    <td>
                        <a class="btn btn-outline-dark" href="/events/${e.id}" role="button">Details</a>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    <div class="container">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:forEach begin="1" end="${page.totalPages}" var="loop">
                    <li class=" ${loop - 1 == page.number ? 'page-item active' : 'page-item'}">
                        <a class="page-link" id="pageHref${loop - 1}" href="#">${loop}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</div>
<script>
    let pageCount = ${page.totalPages};
    let currentUrl = window.location.href.toString().replace('http://localhost:8080', '');
    let urlOfPage = currentUrl.split('page=')[0];
    for (let i = 0; i < pageCount; i++) {
        if (urlOfPage === '/events') {
            $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
        } else if (urlOfPage === '/events?' || urlOfPage.endsWith('&')) {
            $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
        } else {
            $('#pageHref' + i).attr('href', urlOfPage + '&page=' + i);
        }
    }
</script>
</body>
</html>
