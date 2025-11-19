package com.quizmaster.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/quiz_master";
    private static final String MYSQL_USER = "root"; // Update with your DB username
    private static final String MYSQL_PASSWORD = "Hussain17@sql"; // Update with your DB password

    private static final String H2_URL = "jdbc:h2:mem:quiz_master;DB_CLOSE_DELAY=-1;MODE=MYSQL";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        if (Boolean.getBoolean("test")) {
            return DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
        } else {
            return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        }
    }
}