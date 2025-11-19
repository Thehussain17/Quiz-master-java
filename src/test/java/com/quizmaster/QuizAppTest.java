package com.quizmaster;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import javafx.scene.image.WritableImage;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import static java.lang.System.getProperty;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
public class QuizAppTest {

    @BeforeAll
    public static void setup() throws Exception {
        System.setProperty("test", "true");
        try (Connection connection = com.quizmaster.util.DBConnection.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get("sql/schema.sql")));
            statement.execute(sql);

            // Add a test user
            statement.execute("INSERT INTO users (username, email, password) VALUES ('testuser', 'test@test.com', 'password')");
        }
    }

    private QuizApp app;

    @Start
    private void start(Stage stage) {
        app = new QuizApp();
        app.start(stage);
    }

    @Test
    public void testLoginAndNavigateToLeaderboard(FxRobot robot) {
        // Login
        robot.clickOn(".text-field").write("testuser");
        robot.clickOn(".password-field").write("password");
        robot.clickOn("Login");

        // Navigate to Leaderboard
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.clickOn("Leaderboard");

        // Verify that we are on the leaderboard screen
        verifyThat(".label", hasText("Leaderboard"));

        // Take a screenshot
        robot.capture(Paths.get(getProperty("java.io.tmpdir"), "leaderboard.png"));
    }
}