<%-- 
    Document   : AdminHome
    Created on : 18 May 2025, 10:56:11 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() +"/login?message=sessionExpired");
        return;
    }

    // Prevent caching
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    User user = (User) session.getAttribute("user");
    if (!"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect("LoginServlet");
        return;
    }
%>
<html>
    <head>
        <title>Admin Dashboard - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Welcome Admin, <%= user.getName()%>!</h2>
            <p>Use the navigation menu to manage students and courses.</p>
        </div>
        <div class="container mt-5">
            <h4 class="mb-4">Course Enrollment Overview</h4>
            <div class="row">
                <div class="col-md-6">
                    <div class="card shadow-sm p-3">
                        <h5 class="text-center">Bar Chart</h5>
                        <img src="${pageContext.request.contextPath}/chart/enrollment-bar.png" alt="Enrollment Bar Chart" class="img-fluid">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card shadow-sm p-3">
                        <h5 class="text-center">Pie Chart</h5>
                        <img src="${pageContext.request.contextPath}/chart/enrollment-pie.png" alt="Enrollment Pie Chart" class="img-fluid">
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>

