<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Treatments</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Treatments</h2>
    <div>
        <form action="/treatments/filter" method="get">
            <div class="row">
                <div class="col-lg-4">
                    <div class="row mb-3">
                        <label for="tDate" class="col-sm-5 col-form-label">Date</label>
                        <div class="col-lg-7">
                            <input type="date" class="form-control" name="tDate" id="tDate"
                                   value="${param.get("tDate")}">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="insuranceNumber" class="col-sm-5 col-form-label">Insurance number</label>
                        <div class="col-lg-7">
                            <input type="number" class="form-control " name="insuranceNumber" id="insuranceNumber"
                                   min="1" value="${param.get("insuranceNumber")}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <sec:authorize access="hasRole('DOCTOR')">
                        <div class="row mb-3">
                            <label for="authDoctor" class="col-sm-5 col-form-label">Only my treatments</label>
                            <div class="form-group col-lg-2 col-form-label">
                                <div class="form-check form-check-inline">
                                    <input type="checkbox" class="form-check-input" name="authDoctor" id="authDoctor"
                                           <c:if test="${param.get(\"authDoctor\") != null}">checked="checked"</c:if>>
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                    <div class="row mb-3">
                        <label for="onlyOpen" class="col-sm-5 col-form-label">Only open treatments</label>
                        <div class="form-group col-lg-2 col-form-label">
                            <div class="form-check form-check-inline">
                                <input type="checkbox" class="form-check-input" name="onlyOpen" id="onlyOpen"
                                       <c:if test="${param.get(\"onlyOpen\") != null}">checked="checked"</c:if>>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-outline-dark">Filter</button>
        </form>
        <div>
            <a class="btn btn-outline-dark" href="/treatments" role="button">All Treatments</a>
            <a class="btn btn-outline-dark" href="/treatments/today" role="button">Today Treatments</a>
        </div>
    </div>
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
            <th scope="col">Diagnosis</th>
            <th scope="col">Close date</th>
            <th scope="col">Closed</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="t">
            <tr class="${t.closed ? 'table-success' : 'table-warning'}">
                <td style="display: none">${t.id}</td>
                <td style="display: none">${t.patientId}</td>
                <td>${t.patientInsuranceNumber}</td>
                <td>${t.patientName}</td>
                <td style="display: none">${t.doctorId}</td>
                <td>${t.doctorName}</td>
                <td>${t.date}</td>
                <td>${t.diagnosis}</td>
                <td>${t.closeDate}</td>
                <td>${t.closed}</td>
                <sec:authorize access="hasRole('DOCTOR')">
                    <td>
                        <a class="btn btn-outline-dark" href="/treatments/${t.id}" role="button">Details</a>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
    </table>
    <div class="container" id="pageNav">
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
    <sec:authorize access="hasRole('DOCTOR')">
        <a class="btn btn-outline-success" href="/treatments/new" role="button">New Treatment</a>
    </sec:authorize>
</div>
<script>
    let pageCount = ${page.totalPages};
    if (pageCount === 1) {
        $('#pageNav').hide();
    } else {
        let currentUrl = window.location.href.toString().replace('http://localhost:8080', '');
        let urlOfPage = currentUrl.split('page=')[0];
        for (let i = 0; i < pageCount; i++) {
            if (urlOfPage === '/events') {
                $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
            } else if (urlOfPage === '/treatments?' || urlOfPage.endsWith('&')) {
                $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
            } else {
                $('#pageHref' + i).attr('href', urlOfPage + '&page=' + i);
            }
        }
    }
</script>
</body>
</html>
