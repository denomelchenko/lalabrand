DROP DATABASE IF EXISTS lalabrand;

CREATE DATABASE lalabrand;

USE lalabrand;

CREATE TABLE user
(
    `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(255),
    `last_name`  VARCHAR(255),
    `bonus`      INTEGER,
    `email`      VARCHAR(255) UNIQUE NOT NULL,
    `phone`      VARCHAR(255),
    `language`   ENUM ('UA', 'EN'),
    `password`   VARCHAR(255)        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX `email_index` (email)
);


CREATE TABLE `category`
(
    `id`   INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE `item`
(
    `id`              INTEGER PRIMARY KEY AUTO_INCREMENT,
    `title`           VARCHAR(255)                        NOT NULL,
    `short_disc`      VARCHAR(255)                        NOT NULL,
    `long_disc`       VARCHAR(255),
    `rating`          DECIMAL,
    `price`           DECIMAL                             NOT NULL,
    `sold_count`      INTEGER                             NOT NULL,
    `category_id`     INTEGER                             NOT NULL,
    `available_count` INTEGER                             NOT NULL,
    `sale_price`      DECIMAL,
    `image`           VARCHAR(255)                        NOT NULL,
    `created_at`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE
);

CREATE TABLE `address`
(
    `id`       INTEGER PRIMARY KEY AUTO_INCREMENT,
    `country`  ENUM ('UA','PL','DE','US','UK'),
    `zip`      VARCHAR(255) NOT NULL,
    `city`     VARCHAR(255) NOT NULL,
    `address1` VARCHAR(255) NOT NULL,
    `address2` VARCHAR(255) NOT NULL,
    `user_id`  INTEGER      NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `user_roles`
(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
    `role`    ENUM ('ADMIN', 'USER') NOT NULL,
    `user_id` INTEGER,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `cart`
(
    `id`      INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id` INTEGER NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_info`
(
    `color`   ENUM ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `image`   VARCHAR(255)                                                                                           NOT NULL,
    `item_id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `size`
(
    `id`    INTEGER PRIMARY KEY AUTO_INCREMENT,
    `type`  ENUM ('SHOES','CLOTHES') NOT NULL,
    `value` INTEGER(255)             NOT NULL
);

CREATE TABLE `cart_item`
(
    `id`           INTEGER PRIMARY KEY AUTO_INCREMENT,
    `cart_id`      INTEGER NOT NULL,
    `item_id`      INTEGER,
    `item_info_id` INTEGER,
    `size_id`      INTEGER,
    `count`        INTEGER NOT NULL,
    FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`item_info_id`) REFERENCES `item_info` (`item_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `shipping_option`
(
    `id`    INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name`  VARCHAR(100) NOT NULL,
    `price` DECIMAL      NOT NULL
);

CREATE TABLE `shipping_info`
(
    `id`                 INTEGER PRIMARY KEY AUTO_INCREMENT,
    `country`            ENUM ('UA','PL','DE','US','UK'),
    `zip`                VARCHAR(255) NOT NULL,
    `city`               VARCHAR(255) NOT NULL,
    `address1`           VARCHAR(255) NOT NULL,
    `address2`           VARCHAR(255) NOT NULL,
    `phone`              VARCHAR(255) NOT NULL,
    `shipping_option_id` INTEGER,
    FOREIGN KEY (`shipping_option_id`) REFERENCES `shipping_option` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE `order`
(
    `id`           INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id`      INTEGER                             NOT NULL,
    `total_price`  DECIMAL                             NOT NULL,
    `shipping_fee` DECIMAL                             NOT NULL,
    `discount`     DECIMAL,
    `tax`          DECIMAL,
    `shipping_id`  INTEGER,
    `currency`     ENUM ('UAH','EUR','USD')            NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`shipping_id`) REFERENCES `shipping_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `ordered_item`
(
    `id`       INTEGER PRIMARY KEY AUTO_INCREMENT,
    `order_id` INTEGER      NOT NULL,
    `item_id`  INTEGER,
    `title`    VARCHAR(255) NOT NULL,
    `size`     VARCHAR(255) NOT NULL,
    `color`    VARCHAR(255) NOT NULL,
    `price`    DECIMAL      NOT NULL,
    `count`    INTEGER      NOT NULL,
    `image`    VARCHAR(255) NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `item_comment`
(
    `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id`    INTEGER                             NOT NULL,
    `item_id`    INTEGER                             NOT NULL,
    `text`       TEXT                                NOT NULL,
    `rating`     DECIMAL                             NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `look`
(
    `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
    `gender`     ENUM ('MAN','WOMAN', 'UNISEX')      NOT NULL,
    `image`      VARCHAR(255)                        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE `look_item`
(
    `look_id` INTEGER,
    `item_id` INTEGER,
    PRIMARY KEY (`look_id`, `item_id`),
    FOREIGN KEY (`look_id`) REFERENCES `look` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist`
(
    `id`      INTEGER PRIMARY KEY AUTO_INCREMENT,
    `user_id` INTEGER NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist_item`
(
    `wishlist_id` INTEGER,
    `item_id`     INTEGER,
    PRIMARY KEY (`wishlist_id`, `item_id`),
    FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_sizes`
(
    `size_id` INTEGER,
    `item_id` INTEGER,
    PRIMARY KEY (`size_id`, `item_id`),
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `available_colors`
(
    `id`      INTEGER PRIMARY KEY AUTO_INCREMENT,
    `color`   ENUM ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `item_id` INTEGER,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `refresh_token`
(
    `token`      VARCHAR(36) PRIMARY KEY NOT NULL,
    `expires_at` TIMESTAMP               NOT NULL,
    `user_id`    INTEGER                 NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP EVENT IF EXISTS delete_expired_tokens;

DELIMITER //

CREATE EVENT delete_expired_tokens
    ON SCHEDULE EVERY 1 DAY
    DO
    BEGIN
        DELETE FROM refresh_token WHERE expires_at < utc_timestamp();
    END //

DELIMITER ;
