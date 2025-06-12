package dao;

import model.User;
import java.sql.*;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.util.Arrays;

/**
 *
 * @author fakhr
 */
public class UserDAO {

    public User checkLogin(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email = ?";

        System.out.println("[DEBUG] Running SQL: " + sql);
        System.out.println("[DEBUG] With email: " + email);

        try (Connection connection = DBUtil.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
                
                System.out.println("Password bytes: " + Arrays.toString(password.toCharArray()));    
                System.out.println("Entered password: " + password);
                System.out.println("Hash from DB: " + hashedPassword);
                System.out.println("Argon2 type: " + argon2.toString());

                boolean match = argon2.verify(hashedPassword, password.toCharArray());
                System.out.println("Password match? " + match);

                if (argon2.verify(hashedPassword, password.toCharArray())) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    user = new User(id, name, email, role);
                    System.out.println("Login successful for: " + email);
                } else {
                    System.out.println("Invalid password for: " + email);
                }
            } else {
                System.out.println("No user found with email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            ps.executeUpdate();
            System.out.println("✅ Admin user inserted: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String hashPassword(String plainPassword) {
        // Consistent hashing using Argon2i
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
        return argon2.hash(2, 65536, 1, plainPassword.toCharArray());
    }

    public int findUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
}
