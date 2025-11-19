package com.quizmaster.dao;

import com.quizmaster.model.Leaderboard;
import com.quizmaster.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {

    public List<Leaderboard> getGlobalLeaderboard() {
        List<Leaderboard> leaderboard = new ArrayList<>();
        String sql = "SELECT username, " +
                     "(total_correct_answers * 100.0 / total_questions_answered) AS accuracy " +
                     "FROM users WHERE total_questions_answered > 0 " +
                     "ORDER BY accuracy DESC, total_correct_answers DESC LIMIT 100";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                leaderboard.add(new Leaderboard(
                        rank++,
                        rs.getString("username"),
                        rs.getDouble("accuracy")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public List<Leaderboard> getCategoryLeaderboard(String category) {
        List<Leaderboard> leaderboard = new ArrayList<>();
        // This is a more complex query. We need to aggregate scores by user and category.
        String sql = "SELECT u.username, " +
                     "SUM(s.score) * 100.0 / SUM(s.total_questions) AS category_accuracy " +
                     "FROM scores s " +
                     "JOIN users u ON s.user_id = u.id " +
                     "WHERE s.category = ? " +
                     "GROUP BY u.username " +
                     "ORDER BY category_accuracy DESC, SUM(s.score) DESC LIMIT 100";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                int rank = 1;
                while (rs.next()) {
                    leaderboard.add(new Leaderboard(
                            rank++,
                            rs.getString("username"),
                            rs.getDouble("category_accuracy")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
}