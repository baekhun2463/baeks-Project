drop table Item if exists cascade;
CREATE TABLE Item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255) NOT NULL,
    member_id BIGINT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (member_id) REFERENCES Member(id)
);