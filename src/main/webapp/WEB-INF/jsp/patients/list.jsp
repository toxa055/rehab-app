<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Patients</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid">
    <h2>Patients</h2>
    <div>
        <form action="/patients/filter" method="get">
            <div class="row" id="filterDiv">
                <div class="col-lg-4">
                    <div class="row mb-3">
                        <label for="insuranceNumber" class="col-sm-5 col-form-label">Insurance number</label>
                        <div class="col-lg-6">
                            <input type="number" class="form-control " name="insuranceNumber" id="insuranceNumber"
                                   min="1000" max="99999999" value="${param.get("insuranceNumber")}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <div class="row mb-3">
                        <label for="nameLike" class="col-sm-3 col-form-label">Name</label>
                        <div class="col-lg-9">
                            <input type="text" class="form-control " name="nameLike" id="nameLike" minlength="3"
                                   maxlength="18" placeholder="Name like..." value="${param.get("nameLike")}">
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
                    <div class="row mb-3">
                        <label for="onlyTreating" class="col-sm-8 col-form-label">Only treating</label>
                        <div class="form-group col-lg-2 col-form-label">
                            <div class="form-check form-check-inline">
                                <input type="checkbox" class="form-check-input" name="onlyTreating" id="onlyTreating"
                                       <c:if test="${param.get(\"onlyTreating\") != null}">checked="checked"</c:if>>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-2">
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
            <th scope="col">Insurance №</th>
            <th scope="col">Name</th>
            <th scope="col">Birth Date</th>
            <th scope="col">Address</th>
            <th scope="col">State</th>
            <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                <th scope="col">Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="p">
            <tr class="${p.patientState == 'TREATING' ? 'active-yellow' : 'active-green'}">
                <td style="display: none">${p.id}</td>
                <td>${p.insuranceNumber}</td>
                <td>${p.name}</td>
                <td>${p.birthDate}</td>
                <td>${p.address}</td>
                <td>${p.patientState}</td>
                <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                    <td>
                        <a class="btn btn-outline-dark" href="/patients/${p.id}" role="button">Details</a>
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
    <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
        <a class="btn btn-outline-success" href="/patients/new" role="button">New Patient</a>
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
            if (urlOfPage === '/patients/filter') {
                $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
            } else if (urlOfPage === '/patients/filter?' || urlOfPage.endsWith('&')) {
                $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
            } else {
                $('#pageHref' + i).attr('href', urlOfPage + '&page=' + i);
            }
        }
    }
</script>
</body>
</html>
