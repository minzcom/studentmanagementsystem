<%-- 
    Document   : login
    Created on : 18 May 2025, 10:55:12 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.

    String message = request.getParameter("message");
    String error = (String) request.getAttribute("error");
%>

<html>
    <head>
        <title>Login - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="navbar.jsp" />

        <div class="container mt-5" style="max-width: 500px;">
            <h2 class="mb-4">Login</h2>

            <% if ("sessionExpired".equals(message)) { %>
            <div class="alert alert-warning">Your session has expired. Please login again.</div>
            <% } else if ("logout".equals(message)) { %>
            <div class="alert alert-info">You have been logged out successfully.</div>
            <% } %>

            <% if (error != null) {%>
            <div class="alert alert-danger"><%= error%></div>
            <% }%>

            <form action="login" method="post" autocomplete="off">
                <div class="form-group">
                    <label>Email address</label>
                    <input type="email" name="email" required class="form-control" autocomplete="off" />
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" required class="form-control" autocomplete="off" />
                </div>

                <button type="submit" class="btn btn-primary btn-block">Login</button>
            </form>
        </div>

    </body>
</html>


