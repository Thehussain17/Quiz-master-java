package com.quizmaster.service;

import com.quizmaster.dao.AchievementDAO;
import com.quizmaster.dao.QuizDAO;
import com.quizmaster.model.User;

import java.util.List;

public class AchievementService {

    private AchievementDAO achievementDAO;
    private QuizDAO quizDAO;

    public AchievementService() {
        this.achievementDAO = new AchievementDAO();
        this.quizDAO = new QuizDAO();
    }

    public void checkAchievements(User user, int consecutiveCorrectAnswers) {
        // 1. First Blood: Complete one quiz.
        achievementDAO.unlockAchievement(user.getId(), achievementDAO.getAchievementByUnlockCondition("PLAY_1").getId());

        // 2. Streaker: Answer 10 questions in a row correctly.
        if (consecutiveCorrectAnswers >= 10) {
            achievementDAO.unlockAchievement(user.getId(), achievementDAO.getAchievementByUnlockCondition("STREAK_10").getId());
        }

        // 3. Completionist: Complete a quiz in every category.
        List<String> allCategories = quizDAO.getCategories();
        List<String> playedCategories = quizDAO.getPlayedCategories(user.getId());
        if (playedCategories.containsAll(allCategories)) {
            achievementDAO.unlockAchievement(user.getId(), achievementDAO.getAchievementByUnlockCondition("ALL_CATEGORIES").getId());
        }
    }
}
