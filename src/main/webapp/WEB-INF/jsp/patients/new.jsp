<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Add new patient</title>
</head>
<body>
<a href="../">Home</a><br>
<a href="/patients">Patients</a>
<div>
    <p>Add new patient</p>
    <form action="/patients/new" method="post">
        <input type="number" name="insuranceNumber" placeholder="Insurance number"><br>
        <input type="text" name="name" placeholder="Name, Second Name"><br>
        <input type="text" name="address" placeholder="Address"><br>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>
