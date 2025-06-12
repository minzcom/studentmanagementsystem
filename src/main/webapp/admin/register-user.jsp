<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User user = null;
    if (session != null) {
        user = (User) session.getAttribute("user");
    }

    if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
        response.sendRedirect(request.getContextPath() +"/login?message=unauthorized");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
    <head>
        <title>Register User</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script>
            function toggleStudentIdField() {
                const role = document.getElementById("role").value;
                const studentIdDiv = document.getElementById("studentIdDiv");
                studentIdDiv.style.display = (role === "student") ? "block" : "none";
            }
        </script>
    </head>
    <body>
        <jsp:include page="../navbar.jsp" />

        <div class="container mt-5">
            <h2>Register User</h2>

            <form method="post" action="${pageContext.request.contextPath}/admin/register"  autocomplete="off">
                <div class="form-group">
                    <label>Full Name:</label>
                    <input type="text" name="name" required class="form-control" autocomplete="off">
                </div>

                <div class="form-group">
                    <label>Email Address:</label>
                    <input type="email" name="email" required class="form-control" autocomplete="off">
                </div>

                <div class="form-group">
                    <label>Password:</label>
                    <input type="password" name="password" required class="form-control" autocomplete="new-password">
                </div>

                <div class="form-group">
                    <label>User Role:</label>
                    <select name="role" id="role" class="form-control" onchange="toggleStudentIdField()" required>
                        <option value="admin">Admin</option>
                        <option value="student">Student</option>
                    </select>
                </div>

                <div class="form-group" id="studentIdDiv" style="display: none;">
                    <label>Student ID:</label>
                    <input type="text" name="studentNumber" placeholder="e.g. S2025001" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>

    </body>
</html>

