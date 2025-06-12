/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/*
//Uncomment if you want to use db.properties file
import java.io.FileInputStream;
import java.util.Properties;
import java.io.InputStream;
*/

import java.sql.*;

/**
 *
 * @author fakhr
 */
public class DBUtil {

    /* Using system environment, easier to export to docker
    Note:   Add in C:\xampp\catalina_start
            set DB_URL=jdbc:mysql://localhost:3306/studentdb
            set DB_USER=root
            set DB_PASS=
    
            In Railway: 
            MYSQLHOST=containers-us-west-85.railway.app
            MYSQLPORT=5432
            MYSQLDATABASE=railway
            MYSQLUSER=root
            MYSQLPASSWORD=your_secret_password
            
    
    ////////////////////////////////////////
            Windows (CMD):
            cmd
            Copy
            Edit
            set DB_URL=jdbc:mysql://localhost:3306/studentdb
            set DB_USER=root
            set DB_PASS=D1HWAewDXVcpXjH
            Then run your server (Tomcat or Maven) from the same CMD window.

            macOS / Linux / Git Bash:
            bash
            Copy
            Edit
            export DB_URL=jdbc:mysql://localhost:3306/yourdb
            export DB_USER=root
            export DB_PASS=admin
     */
    private static final String JDBC_URL = System.getenv("DB_URL");
    private static final String JDBC_USERNAME = System.getenv("DB_USER");
    private static final String JDBC_PASSWORD = System.getenv("DB_PASS");

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (JDBC_URL == null || JDBC_USERNAME == null || JDBC_PASSWORD == null) {
            throw new SQLException("Missing DB environment variables. Make sure DB_URL, DB_USER, and DB_PASS are set.");
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    /*
    // Uncomment this codes if you are using db.properties file
    
    private static String jdbcURL;
    private static String jdbcUsername;
    private static String jdbcPassword;

    static {
        try (InputStream input = new FileInputStream("C:/studentManagementConfig/db.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            jdbcURL = prop.getProperty("db.url");
            jdbcUsername = prop.getProperty("db.username");
            jdbcPassword = prop.getProperty("db.password");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (jdbcURL == null) {
            throw new SQLException("JDBC URL is null — check db.properties.");
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }*/
}
