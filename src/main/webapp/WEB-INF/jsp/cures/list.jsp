<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Cures</title>
</head>
<body>
<a href="../">Home</a>
<div>
    <p>Cures:</p>
    <c:forEach items="${cures}" var="cure">
        <c:out value="${cure.id}, ${cure.name}, ${cure.cureType}"/><br>
    </c:forEach><br>
</div>
<a href="/cures/new">Add new cure</a>
</body>
</html>
