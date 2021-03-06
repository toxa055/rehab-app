<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Prescriptions</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
    <h2>Prescriptions</h2>
    <div>
        <form action="/prescriptions/filter" method="get">
            <div class="row" id="filterDiv">
                <div class="col-lg-4">
                    <div class="row mb-3">
                        <label for="pDate" class="col-sm-5 col-form-label">Date</label>
                        <div class="col-lg-6">
                            <input type="date" class="form-control" name="pDate" id="pDate"
                                   value="${param.get("pDate")}">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="insuranceNumber" class="col-sm-5 col-form-label">Insurance number</label>
                        <div class="col-lg-6">
                            <input type="number" class="form-control " name="insuranceNumber" id="insuranceNumber"
                                   min="1000" max="99999999" value="${param.get("insuranceNumber")}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <sec:authorize access="hasRole('DOCTOR')">
                        <div class="row mb-3">
                            <label for="authDoctor" class="col-sm-8 col-form-label">Only my prescriptions</label>
                            <div class="form-group col-lg-2 col-form-label">
                                <div class="form-check form-check-inline">
                                    <input type="checkbox" class="form-check-input" name="authDoctor" id="authDoctor"
                                           <c:if test="${param.get(\"authDoctor\") != null}">checked="checked"</c:if>>
                                </div>
                            </div>
                        </div>
                    </sec:authorize>
                    <div class="row mb-3">
                        <label for="onlyActive" class="col-sm-8 col-form-label">Only active prescriptions</label>
                        <div class="form-group col-lg-2 col-form-label">
                            <div class="form-check form-check-inline">
                                <input type="checkbox" class="form-check-input" name="onlyActive" id="onlyActive"
                                       <c:if test="${param.get(\"onlyActive\") != null}">checked="checked"</c:if>>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="row mb-3">
                        <div class="form-group col-lg-2">
                            <button type="submit" class="btn btn-outline-dark">Filter</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col" style="display: none">id</th>
            <th scope="col" style="display: none">Patient id</th>
            <th scope="col">Ins.???</th>
            <th scope="col">Patient</th>
            <th scope="col" style="display: none">Doctor id</th>
            <th scope="col">Doctor</th>
            <th scope="col">Date</th>
            <th scope="col" style="display: none">Cure id</th>
            <th scope="col">Cure</th>
            <th scope="col" style="display: none">Type</th>
            <th scope="col" style="display: none">Pattern id</th>
            <th scope="col">Pattern</th>
            <th scope="col" style="display: none">Period id</th>
            <th scope="col">Period</th>
            <th scope="col">Dose</th>
            <th scope="col">Active</th>
            <sec:authorize access="hasRole('DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="p">
            <tr class="${p.active ? 'active-yellow' : 'active-green'}">
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
                <td>${p.patternCount} times a ${p.patternUnit} (${p.patternUnits})</td>
                <td>${p.periodCount} ${p.periodUnit}</td>
                <td>${p.dose}</td>
                <td>${p.active}</td>
                <sec:authorize access="hasRole('DOCTOR')">
                    <td>
                        <a class="btn btn-outline-dark" href="/prescriptions/${p.id}" role="button">Details</a>
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
</div>
<div id="main"></div>
<jsp:include page="../footer.jsp"/>
<script>
    if (window.location.href.toString().includes('treatment')) {
        $('#filterDiv').hide();
    }
    let pageCount = ${page.totalPages};
    if (pageCount === 1) {
        $('#pageNav').hide();
    } else {
        let currentUrl = window.location.href.toString().replace('http://localhost:8080', '');
        let urlOfPage = currentUrl.split('page=')[0];
        for (let i = 0; i < pageCount; i++) {
            if (urlOfPage === '/prescriptions') {
                $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
            } else if (urlOfPage === '/prescriptions?' || urlOfPage.endsWith('&')) {
                $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
            } else {
                $('#pageHref' + i).attr('href', urlOfPage + '&page=' + i);
            }
            if (urlOfPage.includes('treatment')) {
                if (urlOfPage.includes('?')) {
                    $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
                } else {
                    $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
                }
            }
        }
    }
</script>
</body>
</html>
