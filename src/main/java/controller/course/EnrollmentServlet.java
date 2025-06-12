/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.course;

import dao.StudentDAO;
import dao.CourseDAO;
import dao.EnrollmentDAO;
import model.Student;
import model.Course;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author fakhr
 */
public class EnrollmentServlet extends HttpServlet {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;
    private EnrollmentDAO enrollmentDAO;

    public void init() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
        enrollmentDAO = new EnrollmentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String[] courseIds = request.getParameterValues("courseIds");

            if (courseIds != null) {
                for (String courseIdStr : courseIds) {
                    int courseId = Integer.parseInt(courseIdStr);
                    enrollmentDAO.enrollStudent(studentId, courseId);
                }
            }

            response.sendRedirect(request.getContextPath() + "/student/list");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Student> students = studentDAO.selectAllStudents();
            List<Course> courses = courseDAO.selectAllCourses();

            request.setAttribute("listStudents", students);
            request.setAttribute("listCourses", courses);

            RequestDispatcher dispatcher = request.getRequestDispatcher("student/enroll-student.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
