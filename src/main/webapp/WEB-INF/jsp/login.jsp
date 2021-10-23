<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Log in</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h3>Please login!</h3>
        <form name="f" action="login" method='POST'>
            <div class="row">
                <div class="form-group col-lg-4">
                    <label>Email</label>
                    <input type="email" name="username" class="form-control"/>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-4">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control input-normal"/>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="form-group col-lg-4">
                    <button type="submit" name="submit" class="btn btn-outline-primary">Log In</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
