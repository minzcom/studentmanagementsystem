<%-- 
    Document   : edit-course
    Created on : 18 May 2025, 5:06:50 PM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Course" %>

<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login?message=sessionExpired");
        return;
    }

    Course course = (Course) request.getAttribute("course");
    String error = (String) request.getAttribute("error");

    if (course == null) {
        response.sendRedirect(request.getContextPath() + "/course/list?error=notfound");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
    <head>
        <title>Edit Course - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Edit Course</h2>

            <% if (error != null) {%>
            <div class="alert alert-danger"><%= error%></div>
            <% }%>

            <form action="${pageContext.request.contextPath}/course/update" method="post">
                <input type="hidden" name="id" value="<%= course.getId()%>">
                <div class="form-group">
                    <label>Course Title:</label>
                    <input type="text" name="title" class="form-control"
                           value="<%= course.getTitle() != null ? course.getTitle() : ""%>" required>
                </div>
                <div class="form-group">
                    <label>Description:</label>
                    <textarea name="description" class="form-control" rows="4"><%= course.getDescription() != null ? course.getDescription() : ""%></textarea>
                </div>
                <button type="submit" class="btn btn-warning">Update</button>
                <a href="${pageContext.request.contextPath}/course/list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>

    </body>
</html>

