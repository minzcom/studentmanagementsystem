<%-- 
    Document   : navbar
    Created on : 18 May 2025, 10:48:17 AM
    Author     : fakhr
--%>

<%@ page import="model.User" %>
<%
    User user = null;
    if (session != null) {
        user = (User) session.getAttribute("user");
    }
%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="index.jsp">Student Management System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">

            <% if (user == null) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
            </li>
            <% } else { %>

            <% if ("admin".equalsIgnoreCase(user.getRole())) { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/AdminHome.jsp">Admin Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/list">Manage Students</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/course/list">Manage Courses</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/enrollment">Enroll Students</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/register-user.jsp">Register User</a> <!-- admin-only JSP -->
            </li>
            <% } else { %>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/student/dashboard">Student Dashboard</a>
            </li>
            <% } %>

            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
            <% }%>

        </ul>
    </div>
</nav>



