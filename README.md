Quiz Master

A desktop quiz game built with JavaFX and MySQL.

What is this?

This is a standalone app where you can log in, pick a quiz category (like History or Movies), and answer timed questions. It tracks your score and saves it to a database.

We built this using Java for the app itself and MySQL to store all the users, questions, and scores.

What you need to install first

Before running the code, make sure you have these three things installed:

Java (JDK 17 or 21): To run the code.

Maven: To manage the libraries (like the "pip" of Java).

MySQL: To hold the data.

How to run the project

Step 1: Set up the Database

Open MySQL Workbench.

Run the file sql/schema.sql. This creates the database tables.

Run the file sql/massive_seed.sql. This adds all the questions and categories.

Step 2: Connect the App

Open this file in your code editor: src/main/java/com/quizmaster/util/DBConnection.java

Change the USER and PASSWORD to match your MySQL settings.

private static final String USER = "root";      // Your username
private static final String PASSWORD = "1234";  // Your password


Step 3: Run it!

Open your terminal in the main folder (where pom.xml is) and type:

mvn javafx:run


Project Map (Where stuff lives)

pom.xml: The project settings file. It lists the libraries we are using (JavaFX, MySQL).

sql/:

schema.sql: Creates the tables.

massive_seed.sql: The list of all questions. Edit this to add more quizzes!

src/main/java/...:

QuizApp.java: Start here. This is the main file that handles the UI (Buttons, Windows, Logic).

model/: Simple files (User.java, Question.java) that define what a "User" or "Question" looks like.

dao/: "Database Access Objects". These files do the heavy lifting of talking to the database (loading questions, saving scores).

util/: Helper files (like the Database Connector).

How to add new questions

You don't need to change the Java code to add questions!

Open sql/massive_seed.sql.

Add a new line like this:

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option)
VALUES ('Space', 1, 'Which planet is red?', 'Earth', 'Mars', 'Venus', 'Jupiter', 'B');


Run that command in MySQL.

Restart the app. The new category will appear automatically.

Common Issues

"Connection Failed": You probably forgot to update the password in DBConnection.java.

"Command not found": Make sure you installed Maven and Java correctly and added them to your system path.
