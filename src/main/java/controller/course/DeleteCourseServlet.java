/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.course;

import dao.CourseDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author fakhr
 */

public class DeleteCourseServlet extends HttpServlet {

    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        courseDAO.deleteCourse(id);

        response.sendRedirect(request.getContextPath() + "/course/list");
    }
}
