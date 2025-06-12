<%-- 
    Document   : dashboard
    Created on : 21 May 2025, 3:43:21 PM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Course" %>
<%@ page import="java.util.List" %>

<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login?message=sessionExpired");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String studentName = (String) request.getAttribute("studentName");
    List<Course> enrolledCourses = (List<Course>) request.getAttribute("enrolledCourses");
%>

<html>
    <head>
        <title>Student Dashboard</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h3>Welcome, <%= studentName%>!</h3>
            <h5 class="mt-4">Your Enrolled Courses</h5>

            <% if (enrolledCourses == null || enrolledCourses.isEmpty()) { %>
            <p class="text-muted">You have not enrolled in any courses yet.</p>
            <% } else { %>
            <ul class="list-group">
                <% for (Course c : enrolledCourses) {%>
                <li class="list-group-item">
                    <strong><%= c.getTitle()%></strong><br/>
                    <small><%= c.getDescription()%></small>
                </li>
                <% } %>
            </ul>
            <% }%>
        </div>

    </body>
</html>

