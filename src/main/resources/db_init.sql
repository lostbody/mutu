DROP DATABASE IF EXISTS mutudb;

CREATE DATABASE mutudb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

DROP USER IF EXISTS 'mutuuser'@'localhost';
CREATE USER 'mutuuser'@'localhost' IDENTIFIED BY 'mutupass';
GRANT ALL PRIVILEGES ON mutudb.* TO 'mutuuser'@'localhost';
FLUSH PRIVILEGES;
USE mutudb;
