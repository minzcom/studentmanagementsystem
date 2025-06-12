package dao;

import model.Student;
import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.id AS student_id, u.name, u.email "
                + "FROM students s "
                + "JOIN users u ON s.user_id = u.id "
                + "WHERE u.role = 'student'";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                Student student = new Student(studentId, rs.getString("name"), rs.getString("email"));

                // ✅ Set enrolled courses so the list can display them
                student.setCourses(getCoursesByStudentId(studentId));

                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.title, c.description FROM courses c "
                + "JOIN enrollments e ON c.id = e.course_id "
                + "WHERE e.student_id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void insertStudent(int userId, String studentNumber) {
        String sql = "INSERT INTO students (user_id, student_number) VALUES (?, ?)";
        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, studentNumber);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // not found
    }

    public int findStudentIdByUserId(int userId) {
        String sql = "SELECT id FROM students WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }
}
