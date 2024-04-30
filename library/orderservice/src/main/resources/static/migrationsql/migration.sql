CREATE DATABASE order_service;

CREATE TABLE book_order (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_number` VARCHAR(255)
);

CREATE TABLE order_line_items (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sku_code` VARCHAR(255),
    `price` DECIMAL(19, 2),
    `quantity` INT,
    `order_id` BIGINT,
    FOREIGN KEY (`order_id`) REFERENCES `book_order`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);