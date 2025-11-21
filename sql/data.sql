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

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('History', 1, 'Which ancient civilization built the Pyramids?', 'Romans', 'Greeks', 'Egyptians', 'Persians', 'C'),
('History', 2, 'Who wrote the Communist Manifesto?', 'Lenin', 'Karl Marx', 'Stalin', 'Trotsky', 'B'),
('History', 3, 'Which war was fought between the North and South regions in the USA?', 'World War I', 'World War II', 'Civil War', 'Cold War', 'C'),
('History', 4, 'Who was the British Prime Minister during most of WWII?', 'Neville Chamberlain', 'Winston Churchill', 'Clement Attlee', 'Harold Macmillan', 'B'),
('History', 5, 'Which treaty ended World War I?', 'Treaty of Paris', 'Treaty of Versailles', 'Treaty of Vienna', 'Treaty of Utrecht', 'B');


-- SCIENCE
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Science', 1, 'H2O is the formula for?', 'Gold', 'Oxygen', 'Water', 'Hydrogen', 'C'),
('Science', 2, 'What is the powerhouse of the cell?', 'Nucleus', 'Mitochondria', 'Ribosome', 'Cytoplasm', 'B'),
('Science', 3, 'What is the speed of light?', '300,000 km/s', '150,000 km/s', '1,000 km/s', 'Sound speed', 'A'),
('Science', 4, 'Who developed the theory of relativity?', 'Newton', 'Tesla', 'Einstein', 'Darwin', 'C'),
('Science', 5, 'What is the most abundant gas in Earth atmosphere?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 'C');

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Science', 1, 'What gas do plants release during photosynthesis?', 'Oxygen', 'Nitrogen', 'Carbon Dioxide', 'Helium', 'A'),
('Science', 2, 'Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Mercury', 'B'),
('Science', 3, 'What is the chemical symbol for Sodium?', 'Na', 'S', 'So', 'Sn', 'A'),
('Science', 4, 'Which scientist proposed the three laws of motion?', 'Einstein', 'Newton', 'Galileo', 'Bohr', 'B'),
('Science', 5, 'What part of the atom has a positive charge?', 'Electron', 'Neutron', 'Proton', 'Ion', 'C');

-- GEOGRAPHY
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Geography', 1, 'What is the capital of France?', 'Berlin', 'London', 'Madrid', 'Paris', 'D'),
('Geography', 2, 'Which is the largest ocean?', 'Atlantic', 'Indian', 'Pacific', 'Arctic', 'C'),
('Geography', 3, 'Mount Everest is located in?', 'Andes', 'Rockies', 'Himalayas', 'Alps', 'C'),
('Geography', 4, 'Which country has the most islands?', 'Sweden', 'Indonesia', 'Philippines', 'Japan', 'A'),
('Geography', 5, 'What is the smallest country in the world?', 'Monaco', 'Vatican City', 'San Marino', 'Nauru', 'B');

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Geography', 1, 'Which continent is the Sahara Desert located in?', 'Asia', 'Africa', 'Australia', 'Europe', 'B'),
('Geography', 2, 'Which river is the longest in the world?', 'Amazon', 'Nile', 'Yangtze', 'Mississippi', 'B'),
('Geography', 3, 'Which country is known as the Land of the Rising Sun?', 'China', 'Japan', 'South Korea', 'Thailand', 'B'),
('Geography', 4, 'Which is the deepest ocean trench?', 'Tonga Trench', 'Mariana Trench', 'Philippine Trench', 'Java Trench', 'B'),
('Geography', 5, 'Which desert is the largest hot desert?', 'Gobi Desert', 'Sahara Desert', 'Kalahari Desert', 'Arabian Desert', 'B');


-- PROGRAMMING
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Code', 1, 'HTML stands for?', 'HyperText Markup', 'HighText Machine', 'HyperTool Multi', 'None', 'A'),
('Code', 2, 'Which language is known as the snake?', 'C++', 'Java', 'Python', 'Ruby', 'C'),
('Code', 3, 'Is Java strictly pass-by-reference?', 'Yes', 'No', 'Sometimes', 'Only objects', 'B'),
('Code', 4, 'What is a NullPointerException?', 'A feature', 'Compile Error', 'Runtime Exception', 'Logic Error', 'C'),
('Code', 5, 'Time complexity of Quicksort (worst case)?', 'O(n)', 'O(n log n)', 'O(n^2)', 'O(1)', 'C');

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Code', 1, 'Which of the following is a frontend language?', 'HTML', 'SQL', 'Python', 'C#', 'A'),
('Code', 2, 'Which symbol is used for comments in Python?', '//', '#', '--', '/* */', 'B'),
('Code', 3, 'Which data structure uses FIFO?', 'Stack', 'Queue', 'Tree', 'Graph', 'B'),
('Code', 4, 'Which keyword is used to inherit a class in Java?', 'extends', 'inherits', 'implements', 'super', 'A'),
('Code', 5, 'Which of these is NOT a programming paradigm?', 'OOP', 'Functional', 'Procedural', 'Geographical', 'D');


-- MOVIES
INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Movies', 1, 'Who directed Jurassic Park?', 'Lucas', 'Spielberg', 'Cameron', 'Nolan', 'B'),
('Movies', 2, 'Which movie features the quote "I am your father"?', 'Star Trek', 'Star Wars', 'Alien', 'Matrix', 'B'),
('Movies', 3, 'Who played Jack in Titanic?', 'Brad Pitt', 'Tom Cruise', 'Leo DiCaprio', 'Matt Damon', 'C'),
('Movies', 4, 'The Lord of the Rings director?', 'Peter Jackson', 'George Lucas', 'Tim Burton', 'Ridley Scott', 'A'),
('Movies', 5, 'Highest grossing movie of all time (2023)?', 'Endgame', 'Avatar', 'Titanic', 'Star Wars VII', 'B');

INSERT INTO questions (category, difficulty, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('Movies', 1, 'Which actor played Iron Man in the Marvel Cinematic Universe?', 'Chris Evans', 'Chris Hemsworth', 'Robert Downey Jr.', 'Mark Ruffalo', 'C'),
('Movies', 2, 'Which film won Best Picture at the 2020 Oscars?', '1917', 'Joker', 'Parasite', 'Ford v Ferrari', 'C'),
('Movies', 3, 'Who directed "Pulp Fiction"?', 'Quentin Tarantino', 'Martin Scorsese', 'Guy Ritchie', 'Paul Thomas Anderson', 'A'),
('Movies', 4, 'Which composer wrote the score for "Star Wars"?', 'Hans Zimmer', 'John Williams', 'James Horner', 'Ennio Morricone', 'B'),
('Movies', 5, 'Which film introduced the character "The Bride" (Beatrix Kiddo)?', 'Kill Bill: Vol. 1', 'The Revenant', 'John Wick', 'Mad Max: Fury Road', 'A');