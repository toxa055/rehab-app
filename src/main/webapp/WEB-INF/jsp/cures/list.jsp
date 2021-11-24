<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Cures</title>
</head>
<body>
<jsp:include page="../nav.jsp"/>
<br>
<div class="container-fluid" id="wrap">
    <h2>Cures</h2>
    <div>
        <form action="/cures/filter" method="get">
            <div class="row" id="filterDiv">
                <div class="col-lg-4">
                    <div class="row mb-3">
                        <label for="nameLike" class="col-sm-3 col-form-label">Cure name</label>
                        <div class="col-lg-9">
                            <input type="text" class="form-control " name="nameLike" id="nameLike" minlength="3"
                                   maxlength="30" placeholder="Name like..." value="${param.get("nameLike")}">
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
            <th scope="col">id</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
        </tr>
        </thead>
        <c:forEach items="${page.content}" var="cure">
            <tr class="general-grey">
                <td>${cure.id}</td>
                <td>${cure.name}</td>
                <td>${cure.cureType}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="container" id="pageNav">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:forEach begin="1" end="${page.totalPages}" var="loop">
                    <li class="${loop - 1 == page.number ? 'page-item active' : 'page-item'}">
                        <a class="page-link" id="pageHref${loop - 1}" href="#">${loop}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
    <a class="btn btn-outline-success" href="/cures/new" role="button">New Cure</a>
</div>
<div id="main"></div>
<jsp:include page="../footer.jsp"/>
<script>
    let pageCount = ${page.totalPages};
    if (pageCount === 1) {
        $('#pageNav').hide();
    } else {
        let currentUrl = window.location.href.toString().replace('http://localhost:8080', '');
        let urlOfPage = currentUrl.split('page=')[0];
        for (let i = 0; i < pageCount; i++) {
            if (urlOfPage === '/cures/filter') {
                $('#pageHref' + i).attr('href', urlOfPage + '?page=' + i);
            } else if (urlOfPage === '/cures/filter?' || urlOfPage.endsWith('&')) {
                $('#pageHref' + i).attr('href', urlOfPage + 'page=' + i);
            } else {
                $('#pageHref' + i).attr('href', urlOfPage + '&page=' + i);
            }
        }
    }
</script>
</body>
</html>
