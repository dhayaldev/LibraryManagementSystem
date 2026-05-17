CREATE DATABASE lib;
USE lib;

-- Admin Table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO admin (username, password) VALUES ('Saleem', 'saleem123');
INSERT INTO admin (username, password) VALUES ('Ashraf','ashraf123');

-- Books Table
CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    quantity INT
);

-- Transactions Table
CREATE TABLE transactions (
    trans_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    student_name VARCHAR(100),
    issue_date DATE,
    return_date DATE,
    fine DECIMAL(10,2),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);
