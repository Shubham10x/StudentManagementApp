package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/studentmanagement";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static ResultSet getStudentByUsername(String username) throws Exception {
        Connection conn = getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM student WHERE user_name = ?" // ⚠️ confirm column
        );

        ps.setString(1, username);
        return ps.executeQuery();
    }
}