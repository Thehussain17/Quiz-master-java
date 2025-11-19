package com.quizmaster.dao;

import com.quizmaster.model.Question;
import com.quizmaster.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    public List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions ORDER BY RAND() LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option"),
                    rs.getString("category"),
                    rs.getInt("difficulty") // Now fetching difficulty
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void saveScore(int userId, String category, int score, int total) {
        String query = "INSERT INTO scores (user_id, category, score, total_questions) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            pstmt.setInt(3, score);
            pstmt.setInt(4, total);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}