<%-- 
    Document   : list-courses
    Created on : 18 May 2025, 10:57:45 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Course" %>

<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login?message=sessionExpired");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>


<html>
    <head>
        <title>Courses - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h2>Course List</h2>
                <a href="${pageContext.request.contextPath}/course/add-course.jsp" class="btn btn-primary">➕ Add Course</a>
            </div>

            <table class="table table-bordered table-hover">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th><th>Title</th><th>Description</th><th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Course> listCourse = (List<Course>) request.getAttribute("listCourse");
                        for (Course c : listCourse) {
                    %>
                    <tr>
                        <td><%= c.getId()%></td>
                        <td><%= c.getTitle()%></td>
                        <td><%= c.getDescription()%></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/course/edit?id=<%= c.getId()%>" class="btn btn-sm btn-warning">✏️ Edit</a>
                            <a href="${pageContext.request.contextPath}/course/delete?id=<%= c.getId()%>" class="btn btn-sm btn-danger"
                               onclick="return confirm('Are you sure you want to delete this course?');">🗑️ Delete</a>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>

    </body>
</html>


