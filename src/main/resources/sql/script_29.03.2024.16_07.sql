DROP DATABASE IF EXISTS lalabrand;

CREATE DATABASE lalabrand;

USE lalabrand;

SET time_zone = '+00:00';


CREATE TABLE `user`
(
    `id`         VARCHAR(36) PRIMARY KEY,
    `first_name` VARCHAR(255),
    `last_name`  VARCHAR(255),
    `bonus`      INTEGER,
    `email`      VARCHAR(255) UNIQUE NOT NULL,
    `phone`      VARCHAR(255),
    `language`   ENUM ('UA', 'EN'),
    `password`   VARCHAR(255)        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `category`
(
    `id`   VARCHAR(36) PRIMARY KEY,
    `name` VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE `item`
(
    `id`              VARCHAR(36) PRIMARY KEY             NOT NULL,
    `title`           VARCHAR(255)                        NOT NULL,
    `short_disc`      VARCHAR(255)                        NOT NULL,
    `long_disc`       VARCHAR(255),
    `rating`          DECIMAL,
    `price`           DECIMAL                             NOT NULL,
    `sold_count`      INTEGER                             NOT NULL,
    `category_id`     VARCHAR(36)                         NOT NULL,
    `available_count` INTEGER                             NOT NULL,
    `sale_price`      DECIMAL,
    `image`           VARCHAR(255)                        NOT NULL,
    `created_at`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE
);

CREATE TABLE `address`
(
    `id`       VARCHAR(36) PRIMARY KEY,
    `country`  ENUM ('UA','PL','DE','US','UK'),
    `zip`      VARCHAR(255) NOT NULL,
    `city`     VARCHAR(255) NOT NULL,
    `address1` VARCHAR(255) NOT NULL,
    `address2` VARCHAR(255) NOT NULL,
    `user_id`  VARCHAR(36)  NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `user_roles`
(
    `id`      VARCHAR(36) PRIMARY KEY,
    `role`    ENUM ('ADMIN', 'USER') NOT NULL,
    `user_id` VARCHAR(36),
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE (`user_id`, `role`)
);


CREATE TABLE `cart`
(
    `id`      VARCHAR(36) PRIMARY KEY,
    `user_id` VARCHAR(36) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_info`
(
    `id`                 VARCHAR(36) PRIMARY KEY,
    `color`              ENUM ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `image`              VARCHAR(255)                                                                                           NOT NULL,
    `item_id`            VARCHAR(36)                                                                                            NOT NULL,
    `is_color_available` BOOLEAN                                                                                                NOT NULL,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `size`
(
    `id`    VARCHAR(36) PRIMARY KEY,
    `type`  ENUM ('SHOES','CLOTHES') NOT NULL,
    `value` VARCHAR(5)               NOT NULL
);

CREATE TABLE `cart_item`
(
    `id`           VARCHAR(36) PRIMARY KEY,
    `cart_id`      VARCHAR(36) NOT NULL,
    `item_id`      VARCHAR(36),
    `item_info_id` VARCHAR(36),
    `size_id`      VARCHAR(36),
    `count`        INTEGER     NOT NULL,
    FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`item_info_id`) REFERENCES `item_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `shipping_option`
(
    `id`    VARCHAR(36) PRIMARY KEY,
    `name`  VARCHAR(100) NOT NULL,
    `price` DECIMAL      NOT NULL
);

CREATE TABLE `shipping_info`
(
    `id`                 VARCHAR(36) PRIMARY KEY,
    `country`            ENUM ('UA','PL','DE','US','UK'),
    `zip`                VARCHAR(255) NOT NULL,
    `city`               VARCHAR(255) NOT NULL,
    `address1`           VARCHAR(255) NOT NULL,
    `address2`           VARCHAR(255) NOT NULL,
    `phone`              VARCHAR(255) NOT NULL,
    `shipping_option_id` VARCHAR(36),
    FOREIGN KEY (`shipping_option_id`) REFERENCES `shipping_option` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE `order`
(
    `id`           VARCHAR(36) PRIMARY KEY,
    `user_id`      VARCHAR(36)                         NOT NULL,
    `total_price`  DECIMAL                             NOT NULL,
    `shipping_fee` DECIMAL                             NOT NULL,
    `discount`     DECIMAL,
    `tax`          DECIMAL,
    `shipping_id`  VARCHAR(36),
    `currency`     ENUM ('UAH','EUR','USD')            NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`shipping_id`) REFERENCES `shipping_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `ordered_item`
(
    `id`       VARCHAR(36) PRIMARY KEY,
    `order_id` VARCHAR(36)  NOT NULL,
    `item_id`  VARCHAR(36),
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
    `id`         VARCHAR(36) PRIMARY KEY,
    `user_id`    VARCHAR(36)                         NOT NULL,
    `item_id`    VARCHAR(36)                         NOT NULL,
    `text`       TEXT                                NOT NULL,
    `rating`     DECIMAL                             NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `look`
(
    `id`         VARCHAR(36) PRIMARY KEY,
    `gender`     ENUM ('MAN','WOMAN', 'UNISEX')      NOT NULL,
    `image`      VARCHAR(255)                        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE `look_item`
(
    `look_id` VARCHAR(36),
    `item_id` VARCHAR(36),
    PRIMARY KEY (`look_id`, `item_id`),
    FOREIGN KEY (`look_id`) REFERENCES `look` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist`
(
    `id`      VARCHAR(36) PRIMARY KEY,
    `user_id` VARCHAR(36) NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist_item`
(
    `wishlist_id` VARCHAR(36),
    `item_id`     VARCHAR(36),
    PRIMARY KEY (`wishlist_id`, `item_id`),
    FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_sizes`
(
    `size_id` VARCHAR(36),
    `item_id` VARCHAR(36),
    PRIMARY KEY (`size_id`, `item_id`),
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `refresh_token`
(
    `token`      VARCHAR(36) PRIMARY KEY NOT NULL,
    `expires_at` TIMESTAMP               NOT NULL,
    `user_id`    VARCHAR(36)             NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `password_reset_token`
(
    `id`         VARCHAR(36) PRIMARY KEY NOT NULL,
    `token`      VARCHAR(8)              NOT NULL,
    `expires_at` TIMESTAMP               NOT NULL,
    `user_id`    VARCHAR(36)             NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP EVENT IF EXISTS delete_expired_tokens;

DELIMITER //

CREATE EVENT delete_expired_tokens
    ON SCHEDULE EVERY 1 DAY
    DO
    BEGIN
        DELETE FROM password_reset_token WHERE expires_at < UTC_TIMESTAMP();
        DELETE FROM refresh_token WHERE expires_at < UTC_TIMESTAMP();
    END //

DELIMITER ;
