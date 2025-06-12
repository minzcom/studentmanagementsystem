/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fakhr
 */
public class EnrollmentDAO {

    public void enrollStudent(int studentId, int courseId) {
        // Check if the student is already enrolled before inserting
        String checkSql = "SELECT COUNT(*) FROM enrollments WHERE student_id = ? AND course_id = ?";
        String insertSql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {

            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, studentId);
                    insertStmt.setInt(2, courseId);
                    insertStmt.executeUpdate();
                    System.out.println("✅ Enrolled student_id " + studentId + " in course_id " + courseId);
                }
            } else {
                System.out.println("⚠️ Student " + studentId + " is already enrolled in course " + courseId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Used by AJAX servlet to display enrolled checkboxes
    public List<Integer> getEnrolledCourseIds(int studentId) {
        List<Integer> courseIds = new ArrayList<>();
        String sql = "SELECT course_id FROM enrollments WHERE student_id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                courseIds.add(rs.getInt("course_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courseIds;
    }

}
