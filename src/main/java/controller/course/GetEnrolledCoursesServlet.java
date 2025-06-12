package controller.course;

import dao.EnrollmentDAO;
import dao.CourseDAO;
import model.Course;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author fakhr
 */

public class GetEnrolledCoursesServlet extends HttpServlet {

    private EnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;

    @Override
    public void init() {
        enrollmentDAO = new EnrollmentDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));

            // Get all courses and the ones already enrolled by the student
            List<Course> allCourses = courseDAO.selectAllCourses();
            List<Integer> enrolledCourseIds = enrollmentDAO.getEnrolledCourseIds(studentId);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            for (Course course : allCourses) {
                boolean isEnrolled = enrolledCourseIds.contains(course.getId());

                out.println("<div class='form-check'>");

                out.printf("<input class='form-check-input' type='checkbox' name='courseIds' value='%d' %s>",
                        course.getId(), isEnrolled ? "checked disabled" : "");

                out.printf("<label class='form-check-label'>%s %s</label>",
                        course.getTitle(), isEnrolled ? "(Already enrolled)" : "");

                out.println("</div>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading courses.");
        }
    }
}
