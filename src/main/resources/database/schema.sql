CREATE TABLE IF NOT EXISTS books {
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    category VARCHAR(100),
    rating INT,
    CONSTRAINT CHK_rating CHECK (rating BETWEEN 1 AND 5)
}