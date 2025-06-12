<%-- 
    Document   : index
    Created on : 18 May 2025, 10:47:02 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
    <head>
        <title>Welcome - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="navbar.jsp" />

        <div class="container mt-5">
            <h1 class="text-center">Welcome to the Student Management System</h1>
            <p class="text-center">
                Manage your students and courses easily with this simple portal.
            </p>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

