CREATE TABLE roles
(
    role_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(15)
);

INSERT INTO roles(role_name) VALUES ('ADMIN');
INSERT INTO roles(role_name) VALUES ('USER');