package com.quizmaster.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quiz_master";
    private static final String USER = "root"; // Update with your DB username
    private static final String PASSWORD = "Hussain17@sql"; // Update with your DB password

    private static Connection connection = null;

    // Singleton pattern for connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Connection Failed! Check output console");
                throw e;
            }
        }
        return connection;
    }
}