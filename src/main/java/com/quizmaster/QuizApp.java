package com.quizmaster;

import com.quizmaster.dao.AchievementDAO;
import com.quizmaster.dao.LeaderboardDAO;
import com.quizmaster.dao.QuizDAO;
import com.quizmaster.dao.UserDAO;
import com.quizmaster.model.Achievement;
import com.quizmaster.model.Leaderboard;
import com.quizmaster.model.Question;
import com.quizmaster.service.AchievementService;
import com.quizmaster.model.User;
import com.quizmaster.util.Confetti;
import com.quizmaster.util.Sound;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class QuizApp extends Application {

    private Stage primaryStage;
    private QuizDAO quizDAO;
    private UserDAO userDAO;
    private LeaderboardDAO leaderboardDAO;
    private AchievementDAO achievementDAO;
    private AchievementService achievementService;
    private User currentUser;

    // Special effects
    private Confetti confetti;

    // Game State
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int consecutiveCorrectAnswers = 0;
    private String currentCategory;

    // UI Components
    private Label questionLabel;
    private Label timerLabel;
    private RadioButton rbA, rbB, rbC, rbD;
    private ToggleGroup optionsGroup;
    private ProgressBar progressBar;
    private Timeline timeline;
    private int timeSeconds = 15;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.quizDAO = new QuizDAO();
        this.userDAO = new UserDAO();
        this.leaderboardDAO = new LeaderboardDAO();
        this.achievementDAO = new AchievementDAO();
        this.achievementService = new AchievementService();
        
        primaryStage.setTitle("Quiz Master: Test your knowledge!");
        showLoginScreen();
        primaryStage.show();
    }

    // --- SCENE 1: LOGIN ---
    private void showLoginScreen() {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(40));
        layout.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("Quiz Master");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: white; -fx-font-weight: bold;");

        TextField userField = new TextField(); userField.setPromptText("Username");
        TextField emailField = new TextField(); emailField.setPromptText("Email");
        PasswordField passField = new PasswordField(); passField.setPromptText("Password");
        passField.getStyleClass().add("password-field");
        
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");
        loginBtn.setPrefWidth(200);
        
        Button registerBtn = new Button("Register");
        registerBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");
        registerBtn.setPrefWidth(200);

        loginBtn.setOnAction(e -> {
            User user = userDAO.login(userField.getText(),emailField.getText(), passField.getText());
            if (user != null) {
                currentUser = user;
                showDashboard(); // Go to Dashboard, not straight to quiz
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid credentials.");
            }
        });

        registerBtn.setOnAction(e -> {
            User user = userDAO.register(userField.getText(), "temp@email.com", passField.getText());
            if (user != null) {
                currentUser = user;
                showDashboard();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Username taken.");
            }
        });

        layout.getChildren().addAll(title, userField, passField, loginBtn, registerBtn);
        primaryStage.setScene(new Scene(layout, 400, 550));
    }

    // --- SCENE 2: DASHBOARD (NEW) ---
    private void showDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Header
        HBox header = new HBox(20);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label welcome = new Label("Welcome, " + currentUser.getUsername());
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button leaderboardBtn = new Button("Leaderboard");
        leaderboardBtn.setOnAction(e -> showLeaderboard());

        Button achievementsBtn = new Button("Achievements");
        achievementsBtn.setOnAction(e -> showAchievements());
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            showLoginScreen();
        });
        
        header.getChildren().addAll(welcome, spacer, leaderboardBtn, achievementsBtn, logoutBtn);
        root.setTop(header);

        // Grid of Categories
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        FlowPane grid = new FlowPane();
        grid.setPadding(new Insets(30));
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        // DYNAMIC GENERATION: Fetch categories from DB and create buttons
        List<String> categories = quizDAO.getCategories();
        
        if(categories.isEmpty()) {
             grid.getChildren().add(new Label("No categories found in DB! Run massive_seed.sql"));
        }

        for (String cat : categories) {
            Button catBtn = createCategoryCard(cat);
            grid.getChildren().add(catBtn);
        }

        scrollPane.setContent(grid);
        root.setCenter(scrollPane);
        
        primaryStage.setScene(new Scene(root, 800, 600));
    }

    private Button createCategoryCard(String category) {
        Button btn = new Button(category);
        btn.setPrefSize(150, 150);
        btn.setStyle(
            "-fx-background-color: white; " +
            "-fx-font-size: 18px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #2c3e50; " +
            "-fx-background-radius: 10;"
        );
        
        // Hover effects
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: white; -fx-text-fill: #2c3e50; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10;"));
        
        // Shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.GRAY);
        btn.setEffect(shadow);

        btn.setOnAction(e -> startQuiz(category));
        return btn;
    }

    // --- SCENE 3: QUIZ GAME ---
    private void startQuiz(String category) {
        this.currentCategory = category;
        // Fetch 5 random questions for the SELECTED category
        questionList = quizDAO.getQuestionsByCategory(category, 5); 
        currentQuestionIndex = 0;
        score = 0;

        if (questionList.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "No questions in this category!");
            return;
        }
        initQuizUI();
        loadQuestion();
    }

    private void initQuizUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Top Bar
        VBox topBox = new VBox(10);
        Label catLabel = new Label("Category: " + currentCategory);
        catLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        
        timerLabel = new Label("Time: 15");
        timerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(Double.MAX_VALUE); // Fill width
        
        topBox.getChildren().addAll(catLabel, timerLabel, progressBar);
        topBox.setAlignment(Pos.CENTER);
        root.setTop(topBox);

        // Question Area
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setPadding(new Insets(20, 50, 20, 50));

        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        optionsGroup = new ToggleGroup();
        rbA = styleOption(); rbA.setToggleGroup(optionsGroup);
        rbB = styleOption(); rbB.setToggleGroup(optionsGroup);
        rbC = styleOption(); rbC.setToggleGroup(optionsGroup);
        rbD = styleOption(); rbD.setToggleGroup(optionsGroup);

        centerBox.getChildren().addAll(questionLabel, rbA, rbB, rbC, rbD);
        root.setCenter(centerBox);

        // Bottom Bar
        Button nextButton = new Button("Confirm Answer");
        nextButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 16px;");
        nextButton.setPrefWidth(200);
        nextButton.setOnAction(e -> handleNextButton());
        
        VBox bottomBox = new VBox(nextButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        root.setBottom(bottomBox);

        primaryStage.setScene(new Scene(root, 800, 600));
    }

    private RadioButton styleOption() {
        RadioButton rb = new RadioButton();
        rb.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        return rb;
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            endQuiz();
            return;
        }

        Question q = questionList.get(currentQuestionIndex);
        
        // Display Difficulty stars
        String stars = "★".repeat(q.getDifficulty()) + "☆".repeat(5 - q.getDifficulty());
        questionLabel.setText(stars + "\n" + q.getQuestionText());
        
        rbA.setText(q.getOptionA());
        rbB.setText(q.getOptionB());
        rbC.setText(q.getOptionC());
        rbD.setText(q.getOptionD());

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
            // Check answer based on the RadioButton text? 
            // No, our DB stores "A", "B" but UI shows full text.
            // We need to map UI selection back to A/B/C/D.
            String selectedAnswer = "";
            if (selected == rbA) selectedAnswer = "A";
            else if (selected == rbB) selectedAnswer = "B";
            else if (selected == rbC) selectedAnswer = "C";
            else if (selected == rbD) selectedAnswer = "D";

            if (selectedAnswer.equals(q.getCorrectOption())) {
                score++;
                consecutiveCorrectAnswers++;
            } else {
                consecutiveCorrectAnswers = 0;
            }
        } else {
            consecutiveCorrectAnswers = 0;
        }
        currentQuestionIndex++;
        loadQuestion();
    }

    // --- SCENE 4: RESULTS ---
    private void endQuiz() {
        quizDAO.saveScore(currentUser.getId(), currentCategory, score, questionList.size());

        // Check for achievements
        achievementService.checkAchievements(currentUser, consecutiveCorrectAnswers);

        // Example: if a new achievement is unlocked, play sound and show confetti
        List<Integer> unlockedBefore = achievementDAO.getUnlockedAchievementIds(currentUser.getId());
        achievementService.checkAchievements(currentUser, consecutiveCorrectAnswers);
        List<Integer> unlockedAfter = achievementDAO.getUnlockedAchievementIds(currentUser.getId());

        if (unlockedAfter.size() > unlockedBefore.size()) {
            playAchievementSound();
            triggerConfetti();
        }

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #ecf0f1;");

        Label congrats = new Label("Quiz Completed!");
        congrats.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label scoreLabel = new Label("Score: " + score + " / " + questionList.size());
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #27ae60;");

        Button homeBtn = new Button("Back to Dashboard");
        homeBtn.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-font-size: 16px;");
        homeBtn.setOnAction(e -> showDashboard());

        layout.getChildren().addAll(congrats, scoreLabel, homeBtn);
        primaryStage.setScene(new Scene(layout, 800, 600));
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // --- SCENE 5: LEADERBOARD ---
    private void showLeaderboard() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Header
        Label title = new Label("Leaderboard");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setOnAction(e -> showDashboard());

        HBox topBox = new HBox(20, title, backBtn);
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBox);

        // Table
        TableView<Leaderboard> table = new TableView<>();
        TableColumn<Leaderboard, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));

        TableColumn<Leaderboard, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Leaderboard, Double> accuracyCol = new TableColumn<>("Accuracy %");
        accuracyCol.setCellValueFactory(new PropertyValueFactory<>("accuracy"));

        table.getColumns().addAll(rankCol, userCol, accuracyCol);

        // Category Filter
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("Global");
        categoryFilter.getItems().addAll(quizDAO.getCategories());
        categoryFilter.setValue("Global");

        categoryFilter.setOnAction(e -> {
            String selected = categoryFilter.getValue();
            if (selected.equals("Global")) {
                ObservableList<Leaderboard> data = FXCollections.observableArrayList(leaderboardDAO.getGlobalLeaderboard());
                table.setItems(data);
            } else {
                ObservableList<Leaderboard> data = FXCollections.observableArrayList(leaderboardDAO.getCategoryLeaderboard(selected));
                table.setItems(data);
            }
        });

        // Initial load
        categoryFilter.fireEvent(new ActionEvent());

        VBox centerBox = new VBox(20, categoryFilter, table);
        root.setCenter(centerBox);

        primaryStage.setScene(new Scene(root, 800, 600));
    }

    // --- SCENE 6: ACHIEVEMENTS ---
    private void showAchievements() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Header
        Label title = new Label("Achievements");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setOnAction(e -> showDashboard());

        HBox topBox = new HBox(20, title, backBtn);
        topBox.setAlignment(Pos.CENTER_LEFT);
        root.setTop(topBox);

        // Grid of Achievements
        FlowPane grid = new FlowPane();
        grid.setPadding(new Insets(30));
        grid.setHgap(20);
        grid.setVgap(20);

        List<Achievement> allAchievements = achievementDAO.getAllAchievements();
        List<Integer> unlockedIds = achievementDAO.getUnlockedAchievementIds(currentUser.getId());

        for (Achievement ach : allAchievements) {
            VBox card = new VBox(10);
            card.setPrefSize(150, 150);
            card.setAlignment(Pos.CENTER);
            card.setPadding(new Insets(10));

            Label name = new Label(ach.getName());
            name.setStyle("-fx-font-weight: bold;");
            Label desc = new Label(ach.getDescription());
            desc.setWrapText(true);

            if (unlockedIds.contains(ach.getId())) {
                card.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 10;"); // Unlocked
            } else {
                card.setStyle("-fx-background-color: #bdc3c7; -fx-background-radius: 10;"); // Locked
            }
            card.getChildren().addAll(name, desc);
            grid.getChildren().add(card);
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        primaryStage.setScene(new Scene(root, 800, 600));
    }

    // --- SPECIAL EFFECTS ---
    private void playAchievementSound() {
        Sound.play();
    }

    private void triggerConfetti() {
        // In a real app, you'd use a library or custom Canvas animation.
        Pane root = (Pane) primaryStage.getScene().getRoot();
        confetti = new Confetti(root);
        confetti.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}