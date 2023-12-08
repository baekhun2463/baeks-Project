drop table Post if exists cascade;
CREATE TABLE Post (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        member_id BIGINT NOT NULL,
        title VARCHAR(255) NOT NULL,
        content TEXT NOT NULL,
        author VARCHAR(255),
        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (member_id) REFERENCES Member(id)
);