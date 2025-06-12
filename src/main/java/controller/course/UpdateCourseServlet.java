/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
public class UpdateCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");

            if (title != null) {
                title = title.trim();
            }
            if (description != null) {
                description = description.trim();
            }

            Course updatedCourse = new Course();
            updatedCourse.setId(id);
            updatedCourse.setTitle(title);
            updatedCourse.setDescription(description);

            Course original = courseDAO.getCourseById(id);

            //Prevent changing to a title that already exists in another record
            if (!original.getTitle().equalsIgnoreCase(title) && courseDAO.isTitleExists(title)) {
                request.setAttribute("error", "A course with this title already exists.");
                request.setAttribute("course", updatedCourse);
                request.getRequestDispatcher("/edit-course.jsp").forward(request, response);
                return;
            }

            courseDAO.updateCourse(updatedCourse);
            response.sendRedirect(request.getContextPath() + "/course/list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the course.");
            request.getRequestDispatcher("/edit-course.jsp").forward(request, response);
        }
    }
}
