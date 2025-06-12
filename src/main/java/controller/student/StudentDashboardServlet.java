/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.StudentDAO;
import model.Course;
import model.Student;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author fakhr
 */
public class StudentDashboardServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null || !"student".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login?message=sessionExpired");
            return;
        }

        int studentId = studentDAO.findStudentIdByUserId(user.getId());
        List<Course> courses = studentDAO.getCoursesByStudentId(studentId);

        request.setAttribute("studentName", user.getName());
        request.setAttribute("enrolledCourses", courses);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/student/dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
