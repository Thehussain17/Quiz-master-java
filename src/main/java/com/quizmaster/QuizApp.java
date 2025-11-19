package com.quizmaster;

import com.quizmaster.dao.QuizDAO;
import com.quizmaster.dao.UserDAO;
import com.quizmaster.model.Question;
import com.quizmaster.model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class QuizApp extends Application {

    private Stage primaryStage;
    private QuizDAO quizDAO;
    private UserDAO userDAO;
    private User currentUser; // The logged-in user
    
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    
    // UI Components
    private Label questionLabel;
    private Label timerLabel;
    private RadioButton rbA, rbB, rbC, rbD;
    private ToggleGroup optionsGroup;
    private ProgressBar progressBar;
    
    // Timer
    private Timeline timeline;
    private int timeSeconds = 15;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.quizDAO = new QuizDAO();
        this.userDAO = new UserDAO();

        primaryStage.setTitle("Quiz Master: Enterprise Edition");
        showLoginScreen();
        primaryStage.show();
    }

    // --- SCENE 1: LOGIN / REGISTER ---
    private void showLoginScreen() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("Quiz Master Login");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");

        TextField userField = new TextField();
        userField.setPromptText("Username");
        
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        
        TextField emailField = new TextField(); // Only for registration
        emailField.setPromptText("Email (Register only)");

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        loginBtn.setPrefWidth(200);
        
        Button registerBtn = new Button("Register New User");
        registerBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        registerBtn.setPrefWidth(200);

        // Login Logic
        loginBtn.setOnAction(e -> {
            User user = userDAO.login(userField.getText(), passField.getText());
            if (user != null) {
                currentUser = user;
                startQuiz();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
            }
        });

        // Register Logic
        registerBtn.setOnAction(e -> {
            if(userField.getText().isEmpty() || passField.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Username and Password required.");
                return;
            }
            User user = userDAO.register(userField.getText(), emailField.getText(), passField.getText());
            if (user != null) {
                currentUser = user;
                showAlert(Alert.AlertType.INFORMATION, "Success", "Account created! Starting quiz...");
                startQuiz();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Username likely taken.");
            }
        });

        layout.getChildren().addAll(title, userField, passField, emailField, loginBtn, registerBtn);
        primaryStage.setScene(new Scene(layout, 400, 500));
    }

    // --- SCENE 2: QUIZ GAME ---
    private void startQuiz() {
        questionList = quizDAO.getRandomQuestions(5); 
        currentQuestionIndex = 0;
        score = 0;

        if (questionList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "No questions found.");
            return;
        }
        initQuizUI();
        loadQuestion();
    }

    private void initQuizUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        VBox topBox = new VBox(10);
        Label welcome = new Label("Player: " + currentUser.getUsername());
        welcome.setStyle("-fx-font-weight: bold;");
        
        timerLabel = new Label("Time: 15");
        timerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e74c3c;");
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(600);
        
        topBox.getChildren().addAll(welcome, timerLabel, progressBar);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setPadding(new Insets(20));

        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        optionsGroup = new ToggleGroup();
        rbA = new RadioButton(); rbA.setToggleGroup(optionsGroup);
        rbB = new RadioButton(); rbB.setToggleGroup(optionsGroup);
        rbC = new RadioButton(); rbC.setToggleGroup(optionsGroup);
        rbD = new RadioButton(); rbD.setToggleGroup(optionsGroup);

        centerBox.getChildren().addAll(questionLabel, rbA, rbB, rbC, rbD);
        root.setCenter(centerBox);

        Button nextButton = new Button("Next Question");
        nextButton.setOnAction(e -> handleNextButton());
        
        VBox bottomBox = new VBox(nextButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        root.setBottom(bottomBox);

        primaryStage.setScene(new Scene(root, 600, 500));
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            endQuiz();
            return;
        }

        Question q = questionList.get(currentQuestionIndex);
        questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + q.getQuestionText());
        rbA.setText("A) " + q.getOptionA());
        rbB.setText("B) " + q.getOptionB());
        rbC.setText("C) " + q.getOptionC());
        rbD.setText("D) " + q.getOptionD());

        optionsGroup.selectToggle(null);
        resetTimer();
        progressBar.setProgress((double) currentQuestionIndex / questionList.size());
    }

    private void resetTimer() {
        if (timeline != null) timeline.stop();
        timeSeconds = 15;
        timerLabel.setText("Time: " + timeSeconds);
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            timeSeconds--;
            timerLabel.setText("Time: " + timeSeconds);
            if (timeSeconds <= 0) {
                timeline.stop();
                handleNextButton();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void handleNextButton() {
        timeline.stop();
        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();
        Question q = questionList.get(currentQuestionIndex);
        
        if (selected != null) {
            String selectedOptionChar = selected.getText().substring(0, 1); 
            if (selectedOptionChar.equals(q.getCorrectOption())) {
                score++;
            }
        }
        currentQuestionIndex++;
        loadQuestion();
    }

    // --- SCENE 3: RESULTS ---
    private void endQuiz() {
        // Save score linked to the specific user ID
        quizDAO.saveScore(currentUser.getId(), "General", score, questionList.size());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        
        Label scoreLabel = new Label("Score: " + score + " / " + questionList.size());
        scoreLabel.setStyle("-fx-font-size: 24px;");
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            showLoginScreen();
        });

        layout.getChildren().addAll(new Label("Quiz Finished!"), scoreLabel, logoutBtn);
        primaryStage.setScene(new Scene(layout, 600, 400));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}