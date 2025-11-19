DROP DATABASE IF EXISTS quiz_master;
CREATE DATABASE quiz_master;
USE quiz_master;

-- 1. Users Table (Authentication)
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL, -- In production, hash this!
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Questions Table (Updated with Difficulty)
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    difficulty INT DEFAULT 1 CHECK (difficulty BETWEEN 1 AND 5), -- 1 (Easy) to 5 (Hard)
    question_text VARCHAR(255) NOT NULL,
    option_a VARCHAR(100) NOT NULL,
    option_b VARCHAR(100) NOT NULL,
    option_c VARCHAR(100) NOT NULL,
    option_d VARCHAR(100) NOT NULL,
    correct_option CHAR(1) NOT NULL -- 'A', 'B', 'C', 'D'
);

-- 3. Scores Table
CREATE TABLE scores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(50),
    score INT NOT NULL,
    total_questions INT NOT NULL,
    date_taken TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Achievements
CREATE TABLE achievements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    unlock_condition VARCHAR(255)
);

-- 5. User Achievements
CREATE TABLE user_achievements (
    user_id INT,
    achievement_id INT,
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, achievement_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (achievement_id) REFERENCES achievements(id)
);

-- 6. Friends
CREATE TABLE friends (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    friend_id INT NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'BLOCKED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (friend_id) REFERENCES users(id)
);

-- Seed Data (Updated with Difficulty Ratings)
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('CS', 2, 'What is the complexity of Binary Search?', 'O(n)', 'O(log n)', 'O(n^2)', 'O(1)', 'B'),
('Java', 1, 'Which keyword is used to inherit a class?', 'implements', 'extends', 'inherits', 'super', 'B'),
('Java', 1, 'Default value of boolean?', 'true', 'false', 'null', '0', 'B'),
('CS', 1, 'Which collection stores unique elements?', 'List', 'Map', 'Set', 'Array', 'C'),
('JavaFX', 3, 'Entry point class in JavaFX?', 'Stage', 'Scene', 'Application', 'Platform', 'C'),
('CS', 5, 'Which problem is NP-Complete?', 'Sorting', 'Shortest Path', 'Traveling Salesman', 'Binary Search', 'C');

INSERT INTO achievements (name, description, unlock_condition) VALUES
('First Blood', 'Complete your first quiz', 'PLAY_1'),
('Perfect Score', 'Get 100% on a quiz', 'SCORE_100');