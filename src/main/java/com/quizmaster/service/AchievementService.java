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
        achievementDAO.unlockAchievement(user.getId(), 1); // 1 = First Blood

        // 2. Streaker: Answer 10 questions in a row correctly.
        if (consecutiveCorrectAnswers >= 10) {
            achievementDAO.unlockAchievement(user.getId(), 3); // 3 = Streaker
        }

        // 3. Completionist: Complete a quiz in every category.
        List<String> allCategories = quizDAO.getCategories();
        List<String> playedCategories = quizDAO.getPlayedCategories(user.getId());
        if (playedCategories.containsAll(allCategories)) {
            achievementDAO.unlockAchievement(user.getId(), 4); // 4 = Completionist
        }
    }
}
