package controller.chart;

import dao.StudentDAO;
import model.Student;
import model.Course;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.encoders.EncoderUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 *
 * @author fakhr
 */

public class EnrollmentPieChartServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = studentDAO.selectAllStudents();
        Map<String, Integer> courseCount = new HashMap<>();

        for (Student student : students) {
            List<Course> courses = student.getCourses();
            if (courses != null) {
                for (Course course : courses) {
                    courseCount.put(course.getTitle(), courseCount.getOrDefault(course.getTitle(), 0) + 1);
                }
            }
        }

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Course Enrollment Distribution",
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        BufferedImage chartImage = chart.createBufferedImage(600, 350);
        byte[] imageData = EncoderUtil.encode(chartImage, "png");

        response.setContentType("image/png");
        response.setContentLength(imageData.length);
        OutputStream out = response.getOutputStream();
        out.write(imageData);
        out.close();
    }
}
