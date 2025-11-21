package com.quizmaster.dao;

import com.quizmaster.model.Question;
import com.quizmaster.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {

    // NEW: Fetch unique categories to build the UI dynamically
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM questions ORDER BY category";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // UPDATED: Fetch questions filtered by specific category
    public List<Question> getQuestionsByCategory(String category, int limit) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE category = ? ORDER BY RAND() LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, category);
            pstmt.setInt(2, limit);
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
                    rs.getInt("difficulty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    // Keep the score saving logic
    public void saveScore(int userId, String category, int score, int total) {
        String query = "INSERT INTO scores (user_id, category, score, total_questions) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            pstmt.setInt(3, score);
            pstmt.setInt(4, total);
            pstmt.executeUpdate();

            // NEW: Update the user's global stats for the leaderboard
            updateUserStats(userId, score, total);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserStats(int userId, int correctAnswers, int totalQuestions) {
        String query = "UPDATE users SET total_correct_answers = total_correct_answers + ?, " +
                       "total_questions_answered = total_questions_answered + ? " +
                       "WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, correctAnswers);
            pstmt.setInt(2, totalQuestions);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPlayedCategories(int userId) {
        List<String> playedCategories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM scores WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    playedCategories.add(rs.getString("category"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playedCategories;
    }
<<<<<<< HEAD
=======

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
                    rs.getInt("difficulty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
>>>>>>> feature/all-categories-quiz
}