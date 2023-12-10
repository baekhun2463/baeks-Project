DROP TABLE IF EXISTS Comment CASCADE;
CREATE TABLE Comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         post_id BIGINT NOT NULL,
                         author VARCHAR(255),
                         content TEXT NOT NULL,
                         created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (post_id) REFERENCES Post(id)
);