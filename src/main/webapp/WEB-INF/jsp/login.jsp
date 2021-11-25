<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Log in</title>
</head>
<body style="background-image: url('/img/cover.png'); background-size: cover">
<jsp:include page="nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h2 class="mb-3">Please login!</h2>
        <form name="f" action="login" method='POST'>
            <div class="col-lg-4">
                <div class="row mb-1">
                    <div class="form-group col-lg-12">
                        <c:choose>
                            <c:when test="${param.error == true}">
                                <div class="alert alert-danger" role="alert">Incorrect email or password. Try again.
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="form-group col-lg-12">
                        <label for="email" class="col-sm-2 col-form-label">Email</label>
                        <input type="email" name="username" id="email" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="form-group col-lg-12">
                        <label for="password" class="col-sm-2 col-form-label">Password</label>
                        <input type="password" name="password" id="password" class="form-control"/>
                    </div>
                </div>
                <br>
                <div class="row mb-3">
                    <div class="form-group col-lg-12">
                        <button type="submit" name="submit" class="btn btn-outline-primary">Log In</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="fixed-bottom">
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>
