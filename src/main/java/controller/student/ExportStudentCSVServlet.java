package controller.student;

import dao.StudentDAO;
import model.Student;
import model.Course;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author fakhr
 */

public class ExportStudentCSVServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = studentDAO.selectAllStudents();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=students.csv");

        try (PrintWriter out = response.getWriter()) {
            out.println("ID,Name,Email,Enrolled Courses");
            
            for (Student s : students) {
                StringBuilder courses = new StringBuilder();
                List<Course> courseList = s.getCourses();
                if (courseList != null && !courseList.isEmpty()) {
                    for (Course c : courseList) {
                        courses.append(c.getTitle().replace(",", " ")).append("; ");
                    }
                } else {
                    courses.append("No Enrollment");
                }
                
                out.printf("%d,%s,%s,\"%s\"%n",
                        s.getId(),
                        s.getName(),
                        s.getEmail(),
                        courses.toString().trim());
            }
            
            out.flush();
        }
    }
}
