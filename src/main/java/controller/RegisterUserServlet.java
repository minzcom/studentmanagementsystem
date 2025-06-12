package controller;

import dao.UserDAO;
import dao.StudentDAO;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegisterUserServlet extends HttpServlet {

    private UserDAO userDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String plainPassword = request.getParameter("password");
        String role = request.getParameter("role");
        String studentNumber = request.getParameter("studentNumber"); // optional

        // Hash the password
        String hashedPassword = userDAO.hashPassword(plainPassword);

        // Create User object
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);

        // Insert into users table
        userDAO.insertUser(newUser);

        // If student, insert into students table too
        if ("student".equalsIgnoreCase(role)) {
            int userId = userDAO.findUserIdByEmail(email);

            // If no student ID was manually entered, generate one
            if (studentNumber == null || studentNumber.trim().isEmpty()) {
                studentNumber = generateStudentNumber(userId);
            }

            studentDAO.insertStudent(userId, studentNumber);
            System.out.println("Inserted into students: " + studentNumber);
        }

        // Redirect to login or admin dashboard
        response.sendRedirect(request.getContextPath() + "/admin/AdminHome.jsp");
    }

    private String generateStudentNumber(int userId) {
        return "S" + (2025000 + userId); // example: S2025001
    }
}
