<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Error</title>
</head>
<body>
<jsp:include page="nav.jsp"/>
<br>
<div class="container-fluid">
    <div class="container">
        <h2 class="mb-4">${exception != null ? exception : 'Unknown error or page not found...'}</h2>
        <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Back</button>
    </div>
</div>
</body>
</html>
