CREATE TABLE users
(
    id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(30)         NOT NULL,
    email    VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(100)         NOT NULL,
    role_id  BIGINT,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

INSERT INTO users (name, email, password, role_id)
VALUES ('admin', 'bookstore.admin@gmail.com', '$2a$12$yIIRw9D.RF/JGu2.up8clOIMPAkFIhAT0b1IxmPY9uFDUqUMnun5e', 1);