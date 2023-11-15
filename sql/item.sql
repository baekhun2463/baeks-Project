drop table Item if exists cascade;
CREATE TABLE Item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    memberId BIGINT NOT NULL,
    itemName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (memberId) REFERENCES Member(id)
);