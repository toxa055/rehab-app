<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Error</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<br>
<div class="container">
    <div class="mb-4">
        <h3 class="mb-4">Something went wrong...</h3>
        <h5>${exception != null ? exception : 'Page not found.'}</h5>
    </div>
    <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
</div>
</body>
</html>
