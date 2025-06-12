/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package dao;

import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fakhr
 */

public class CourseDAO {

    public List<Course> selectAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

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

    public Course getCourseById(int id) {
        Course course = null;
        String sql = "SELECT * FROM courses WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course = new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }

    public boolean insertCourse(Course course) {
        if (isTitleExists(course.getTitle())) {
            System.out.println("⚠️ Course title already exists: " + course.getTitle());
            return false;
        }

        String sql = "INSERT INTO courses (title, description) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET title = ?, description = ? WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTitleExists(String title) {
        String sql = "SELECT COUNT(*) FROM courses WHERE LOWER(title) = LOWER(?)";

        try (Connection connection = DBUtil.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, title.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
