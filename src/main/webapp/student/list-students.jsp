<%-- 
    Document   : list-students
    Created on : 18 May 2025, 10:56:57 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
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
        <title>Students - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Student List</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th><th>Name</th><th>Email</th><th>Enrolled Courses</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Student> listStudent = (List<Student>) request.getAttribute("listStudent");
                        for (Student s : listStudent) {
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getEmail()%></td>
                        <td>
                            <%
                                List<Course> courses = s.getCourses();
                                if (courses != null) {
                                    for (Course c : courses) {
                                        out.print(c.getTitle() + "<br>");
                                    }
                                } else {
                                    out.print("<i>No Enrollment</i>");
                                }
                            %>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/export/students.csv" class="btn btn-success mb-3">Export to CSV</a>
            <a href="${pageContext.request.contextPath}/export/students.pdf" class="btn btn-danger mb-3">Export to PDF</a>
        </div>

    </body>
</html>

