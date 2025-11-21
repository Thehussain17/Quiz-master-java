package com.quizmaster.dao;

import com.quizmaster.model.Achievement;
import com.quizmaster.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAO {

    public List<Achievement> getAllAchievements() {
        List<Achievement> achievements = new ArrayList<>();
        String sql = "SELECT * FROM achievements";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                achievements.add(new Achievement(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("unlock_condition")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return achievements;
    }

    public List<Integer> getUnlockedAchievementIds(int userId) {
        List<Integer> unlockedIds = new ArrayList<>();
        String sql = "SELECT achievement_id FROM user_achievements WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    unlockedIds.add(rs.getInt("achievement_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unlockedIds;
    }

    public void unlockAchievement(int userId, int achievementId) {
        String sql = "INSERT INTO user_achievements (user_id, achievement_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, achievementId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Ignore if the achievement is already unlocked (duplicate key)
        }
    }
}