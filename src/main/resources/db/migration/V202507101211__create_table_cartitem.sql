CREATE TABLE cart_item
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    book_id  BIGINT NOT NULL,
    quantity INT    NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES books (id)
);