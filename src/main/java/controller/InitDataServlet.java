package controller;

import dao.DBUtil;
import dao.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author fakhr
 */
// to be ran before login http://localhost:8080/setup/init-data?token=secret123
public class InitDataServlet extends HttpServlet {

    private static final String INIT_TOKEN = "secret123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (!INIT_TOKEN.equals(token)) {
            response.setStatus(403);
            response.setContentType("text/plain");
            response.getWriter().println("Access denied. Invalid token.");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head>");
        out.println("<meta http-equiv='refresh' content='3;URL=" + request.getContextPath() + "/login'>");
        out.println("<title>Initializing...</title></head><body>");
        out.println("<h3>Initializing system data...</h3><pre>");

        try (Connection conn = DBUtil.getConnection()) {
            UserDAO userDAO = new UserDAO();

            updatePassword(out, conn, userDAO, "john@student.com", "123456");
            updatePassword(out, conn, userDAO, "admin@admin.com", "123456");
            updatePassword(out, conn, userDAO, "azrul@student.com", "123456");
            updatePassword(out, conn, userDAO, "azman@admin.com", "123456");
            updatePassword(out, conn, userDAO, "azroy@student.com", "123456");
            updatePassword(out, conn, userDAO, "ali@student.com", "123456");

            out.println("</pre><p>Passwords updated. Redirecting to login in 3 seconds...</p>");
        } catch (Exception e) {
            out.println("</pre><p style='color:red;'>Failed to update passwords:</p><pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("</body></html>");
    }

    private void updatePassword(PrintWriter out, Connection conn, UserDAO userDAO, String email, String newPlainPassword) {
        try {
            String hashedPassword = userDAO.hashPassword(newPlainPassword);
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
            ps.setString(1, hashedPassword);
            ps.setString(2, email);
            int affected = ps.executeUpdate();

            if (affected > 0) {
                out.println("Password updated for: " + email);
            } else {
                out.println("User not found: " + email);
            }
        } catch (Exception e) {
            out.println("Error updating: " + email);
            e.printStackTrace(out);
        }
    }
}
