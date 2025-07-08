CREATE TABLE books
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    pages        INT,
    published    DATE,
    price        DOUBLE,
    category     VARCHAR(50),
    availability VARCHAR(50),
    author_id    BIGINT,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES authors (id)
);