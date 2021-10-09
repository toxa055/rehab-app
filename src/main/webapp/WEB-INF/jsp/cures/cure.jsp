<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>${cure.name}</title>
</head>
<body>
<a href="../">Home</a><br>
<a href="/cures">Cures</a>
<div>
    <c:out value="${cure.id}, ${cure.name}, ${cure.cureType}"/><br>
</div>
</body>
</html>
