package controller.course;

import dao.CourseDAO;
import model.Course;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author fakhr
 */
public class AddCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title").trim();
        String description = request.getParameter("description");

        Course newCourse = new Course();
        newCourse.setTitle(title);
        newCourse.setDescription(description);

        boolean success = courseDAO.insertCourse(newCourse);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/course/list");
        } else {
            request.setAttribute("error", "A course with the same title already exists.");
            request.setAttribute("prefill", newCourse); 
            RequestDispatcher dispatcher = request.getRequestDispatcher("/course/add-course.jsp");
            dispatcher.forward(request, response);
        }
    }
}
