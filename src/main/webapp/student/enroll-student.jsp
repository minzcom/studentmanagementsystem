<%-- 
    Document   : enroll-student
    Created on : 18 May 2025, 10:58:20 AM
    Author     : fakhr
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
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
        <title>Enroll Students - Student Management System</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script>
            function loadCoursesForStudent(studentId) {
                const xhr = new XMLHttpRequest();
                xhr.open("GET", "<%= request.getContextPath()%>/enrollment/courses?studentId=" + studentId, true);
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        document.getElementById("courseList").innerHTML = xhr.responseText;
                    }
                };
                xhr.send();
            }

            window.onload = function () {
                const studentSelect = document.getElementById("studentSelect");
                loadCoursesForStudent(studentSelect.value);
                studentSelect.addEventListener("change", function () {
                    loadCoursesForStudent(this.value);
                });
            };
        </script>
    </head>
    <body>

        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Enroll Student into Courses</h2>

            <form action="${pageContext.request.contextPath}/enrollment" method="post">
                <div class="form-group">
                    <label>Select Student:</label>
                    <select name="studentId" id="studentSelect" class="form-control" required>
                        <%
                            List<Student> listStudents = (List<Student>) request.getAttribute("listStudents");
                            for (Student student : listStudents) {
                        %>
                        <option value="<%= student.getId()%>">
                            <%= student.getName()%> - <%= student.getEmail()%>
                        </option>
                        <% }%>
                    </select>
                </div>

                <div class="form-group">
                    <label>Select Courses:</label>
                    <div id="courseList">
                        <%-- Course checkboxes will be loaded here dynamically --%>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Enroll</button>
            </form>
        </div>

    </body>
</html>


