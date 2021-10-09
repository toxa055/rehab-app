<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html: charset=UTF-8">
    <title>Add new cure</title>
</head>
<body>
<a href="../">Home</a><br>
<a href="/cures">Cures</a>
<div>
    <p>Add new cure</p>
    <form action="/cures/new" method="post">
        <input type="text" name="name" placeholder="Name"><br>
        <select name="cureType">
            <option>MEDICINE</option>
            <option>PROCEDURE</option>
        </select><br>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>
