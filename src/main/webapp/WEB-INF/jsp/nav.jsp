<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #cfc7ff">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Rehab App</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <sec:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="/employees">Employees</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/patients">Patients</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                    <li class="nav-item">
                        <a class="nav-link" href="/treatments/today">Treatments</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
                    <li class="nav-item">
                        <a class="nav-link" href="/prescriptions/today">Prescriptions</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')">
                    <li class="nav-item">
                        <a class="nav-link" href="/events/today">Events</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/cures">Cures</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Log In</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/employees/profile">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Log Out</a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
