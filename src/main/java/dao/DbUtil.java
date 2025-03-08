package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
    private static Connection conn = null;

    // Database credentials (Modify as needed)
    private static final String URL = "jdbc:mysql://localhost:3306/todo_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Private constructor to prevent instantiation
    private DbUtil() {}

    // Method to establish or return the existing database connection
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Establish database connection
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
        return conn;
    }

    // Method to close the connection (optional)
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("⚠️ Error closing connection: " + e.getMessage());
        }
    }
}
