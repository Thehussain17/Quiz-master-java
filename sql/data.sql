USE quiz_master;

-- Clear existing questions to avoid duplicates
TRUNCATE TABLE questions;

-- HISTORY
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('History', 1, 'Who was the first President of the USA?', 'Jefferson', 'Lincoln', 'Washington', 'Adams', 'C'),
('History', 2, 'In which year did WWII end?', '1944', '1945', '1946', '1939', 'B'),
('History', 3, 'Who was the French emperor at Waterloo?', 'Louis XVI', 'Charlemagne', 'Napoleon', 'Robespierre', 'C'),
('History', 4, 'Which empire was ruled by Genghis Khan?', 'Ottoman', 'Mongol', 'Roman', 'Persian', 'B'),
('History', 5, 'The library of Alexandria was in which country?', 'Greece', 'Rome', 'Egypt', 'Persia', 'C');

-- SCIENCE
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Science', 1, 'H2O is the formula for?', 'Gold', 'Oxygen', 'Water', 'Hydrogen', 'C'),
('Science', 2, 'What is the powerhouse of the cell?', 'Nucleus', 'Mitochondria', 'Ribosome', 'Cytoplasm', 'B'),
('Science', 3, 'What is the speed of light?', '300,000 km/s', '150,000 km/s', '1,000 km/s', 'Sound speed', 'A'),
('Science', 4, 'Who developed the theory of relativity?', 'Newton', 'Tesla', 'Einstein', 'Darwin', 'C'),
('Science', 5, 'What is the most abundant gas in Earth atmosphere?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 'C');

-- GEOGRAPHY
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Geography', 1, 'What is the capital of France?', 'Berlin', 'London', 'Madrid', 'Paris', 'D'),
('Geography', 2, 'Which is the largest ocean?', 'Atlantic', 'Indian', 'Pacific', 'Arctic', 'C'),
('Geography', 3, 'Mount Everest is located in?', 'Andes', 'Rockies', 'Himalayas', 'Alps', 'C'),
('Geography', 4, 'Which country has the most islands?', 'Sweden', 'Indonesia', 'Philippines', 'Japan', 'B'),
('Geography', 5, 'What is the smallest country in the world?', 'Monaco', 'Vatican City', 'San Marino', 'Nauru', 'B');

-- PROGRAMMING
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Code', 1, 'HTML stands for?', 'HyperText Markup', 'HighText Machine', 'HyperTool Multi', 'None', 'A'),
('Code', 2, 'Which language is known as the snake?', 'C++', 'Java', 'Python', 'Ruby', 'C'),
('Code', 3, 'Is Java strictly pass-by-reference?', 'Yes', 'No', 'Sometimes', 'Only objects', 'B'),
('Code', 4, 'What is a NullPointerException?', 'A feature', 'Compile Error', 'Runtime Exception', 'Logic Error', 'C'),
('Code', 5, 'Time complexity of Quicksort (worst case)?', 'O(n)', 'O(n log n)', 'O(n^2)', 'O(1)', 'C');

-- MOVIES
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Movies', 1, 'Who directed Jurassic Park?', 'Lucas', 'Spielberg', 'Cameron', 'Nolan', 'B'),
('Movies', 2, 'Which movie features the quote "I am your father"?', 'Star Trek', 'Star Wars', 'Alien', 'Matrix', 'B'),
('Movies', 3, 'Who played Jack in Titanic?', 'Brad Pitt', 'Tom Cruise', 'Leo DiCaprio', 'Matt Damon', 'C'),
('Movies', 4, 'The Lord of the Rings director?', 'Peter Jackson', 'George Lucas', 'Tim Burton', 'Ridley Scott', 'A'),
('Movies', 5, 'Highest grossing movie of all time (2023)?', 'Endgame', 'Avatar', 'Titanic', 'Star Wars VII', 'B');