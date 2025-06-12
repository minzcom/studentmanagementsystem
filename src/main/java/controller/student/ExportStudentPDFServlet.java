package controller.student;

import dao.StudentDAO;
import model.Student;
import model.Course;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author fakhr
 */

public class ExportStudentPDFServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=students.pdf");

        try {
            List<Student> students = studentDAO.selectAllStudents();
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font tableHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font tableBody = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("Student List with Enrollments", titleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 4, 6});

            table.addCell(new PdfPCell(new Phrase("ID", tableHeader)));
            table.addCell(new PdfPCell(new Phrase("Name", tableHeader)));
            table.addCell(new PdfPCell(new Phrase("Email", tableHeader)));
            table.addCell(new PdfPCell(new Phrase("Enrolled Courses", tableHeader)));

            for (Student s : students) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(s.getId()), tableBody)));
                table.addCell(new PdfPCell(new Phrase(s.getName(), tableBody)));
                table.addCell(new PdfPCell(new Phrase(s.getEmail(), tableBody)));

                StringBuilder enrolled = new StringBuilder();
                List<Course> courses = s.getCourses();
                if (courses != null && !courses.isEmpty()) {
                    for (Course c : courses) {
                        enrolled.append("• ").append(c.getTitle()).append("\n");
                    }
                } else {
                    enrolled.append("No Enrollment");
                }
                table.addCell(new PdfPCell(new Phrase(enrolled.toString(), tableBody)));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
