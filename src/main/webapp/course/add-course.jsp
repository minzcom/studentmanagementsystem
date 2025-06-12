<%-- 
    Document   : add-course
    Created on : 18 May 2025, 5:05:38 PM
    Author     : fakhr
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Course" %>

<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login?message=sessionExpired");
        return;
    }

    Course prefill = (Course) request.getAttribute("prefill");
    String error = (String) request.getAttribute("error");

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<html>
    <head>
        <title>Add Course - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Add New Course</h2>

            <% if (error != null) {%>
            <div class="alert alert-danger"><%= error%></div>
            <% }%>

            <form action="${pageContext.request.contextPath}/course/add" method="post">
                <div class="form-group">
                    <label>Course Title:</label>
                    <input type="text" name="title" class="form-control"
                           value="<%= prefill != null ? prefill.getTitle() : ""%>" required>
                </div>
                <div class="form-group">
                    <label>Description:</label>
                    <textarea name="description" class="form-control" rows="4"><%= prefill != null ? prefill.getDescription() : ""%></textarea>
                </div>
                <button type="submit" class="btn btn-success">Save Course</button>
                <a href="${pageContext.request.contextPath}/course/list" class="btn btn-secondary">Cancel</a>
            </form>
        </div>

    </body>
</html>

