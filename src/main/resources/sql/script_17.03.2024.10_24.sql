DROP DATABASE IF EXISTS lalabrand;

CREATE DATABASE lalabrand;

USE lalabrand;

CREATE TABLE `user`
(
    `id`         integer PRIMARY KEY AUTO_INCREMENT,
    `username`   varchar(255)        NOT NULL,
    `first_name` varchar(255),
    `last_name`  varchar(255),
    `bonus`      integer,
    `email`      varchar(255) UNIQUE NOT NULL,
    `phone`      varchar(255),
    `language`   enum ('UA', 'EN'),
    `password`   varchar(255)        NOT NULL,
    `created_at` timestamp           NOT NULL
);

CREATE TABLE `category`
(
    `id`   integer PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) UNIQUE NOT NULL
);

CREATE TABLE `item`
(
    `id`              integer PRIMARY KEY AUTO_INCREMENT,
    `title`           varchar(255) NOT NULL,
    `short_disc`      varchar(255) NOT NULL,
    `long_disc`       varchar(255),
    `rating`          decimal,
    `price`           decimal      NOT NULL,
    `sold_count`      integer,
    `category_id`     integer      NOT NULL,
    `available_count` integer      NOT NULL,
    `sale_price`      decimal,
    `image`           varchar(255) NOT NULL,
    `created_at`      timestamp    NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE
);

CREATE TABLE `address`
(
    `id`       integer PRIMARY KEY AUTO_INCREMENT,
    `country`  enum ('UA','PL','DE','US','UK'),
    `zip`      varchar(255) NOT NULL,
    `city`     varchar(255) NOT NULL,
    `address1` varchar(255) NOT NULL,
    `address2` varchar(255) NOT NULL,
    `user_id`  integer      NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `user_roles`
(
    id        integer PRIMARY KEY AUTO_INCREMENT,
    `role`    enum ('ADMIN', 'USER') NOT NULL,
    `user_id` integer,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `cart`
(
    `id`      integer PRIMARY KEY AUTO_INCREMENT,
    `user_id` integer NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_info`
(
    `color`   enum ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `image`   varchar(255)                                                                                           NOT NULL,
    `item_id` integer PRIMARY KEY AUTO_INCREMENT,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `size`
(
    `id`    integer PRIMARY KEY AUTO_INCREMENT,
    `type`  enum ('SHOES','CLOTHES') NOT NULL,
    `value` varchar(255)             NOT NULL
);

CREATE TABLE `cart_item`
(
    `id`           integer PRIMARY KEY AUTO_INCREMENT,
    `cart_id`      integer NOT NULL,
    `item_id`      integer,
    `item_info_id` integer,
    `size_id`      integer,
    `count`        integer NOT NULL,
    FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`item_info_id`) REFERENCES `item_info` (`item_id`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `shipping_option`
(
    `id`    integer PRIMARY KEY AUTO_INCREMENT,
    `name`  varchar(100) NOT NULL,
    `price` decimal      NOT NULL
);

CREATE TABLE `shipping_info`
(
    `id`                 integer PRIMARY KEY AUTO_INCREMENT,
    `country`            enum ('UA','PL','DE','US','UK'),
    `zip`                varchar(255) NOT NULL,
    `city`               varchar(255) NOT NULL,
    `address1`           varchar(255) NOT NULL,
    `address2`           varchar(255) NOT NULL,
    `phone`              varchar(255) NOT NULL,
    `shipping_option_id` integer,
    FOREIGN KEY (`shipping_option_id`) REFERENCES `shipping_option` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE `order`
(
    `id`           integer PRIMARY KEY AUTO_INCREMENT,
    `user_id`      integer                  NOT NULL,
    `total_price`  decimal                  NOT NULL,
    `shipping_fee` decimal                  NOT NULL,
    `discount`     decimal,
    `tax`          decimal,
    `shipping_id`  integer,
    `currency`     enum ('UAH','EUR','USD') NOT NULL,
    `created_at`   timestamp                NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`shipping_id`) REFERENCES `shipping_info` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `ordered_item`
(
    `id`       integer PRIMARY KEY AUTO_INCREMENT,
    `order_id` integer      NOT NULL,
    `item_id`  integer,
    `title`    varchar(255) NOT NULL,
    `size`     varchar(255) NOT NULL,
    `color`    varchar(255) NOT NULL,
    `price`    decimal      NOT NULL,
    `count`    integer      NOT NULL,
    `image`    varchar(255) NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE `item_comment`
(
    `id`         integer PRIMARY KEY AUTO_INCREMENT,
    `user_id`    integer   NOT NULL,
    `item_id`    integer   NOT NULL,
    `text`       text      NOT NULL,
    `rating`     decimal   NOT NULL,
    `created_at` timestamp NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `look`
(
    `id`         integer PRIMARY KEY AUTO_INCREMENT,
    `gender`     enum ('MAN','WOMAN', 'UNISEX') NOT NULL,
    `image`      varchar(255)                   NOT NULL,
    `created_at` datetime                       NOT NULL
);

CREATE TABLE `look_item`
(
    `look_id` integer,
    `item_id` integer,
    PRIMARY KEY (`look_id`, `item_id`),
    FOREIGN KEY (`look_id`) REFERENCES `look` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist`
(
    `id`      integer PRIMARY KEY AUTO_INCREMENT,
    `user_id` integer NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `wishlist_item`
(
    `wishlist_id` integer,
    `item_id`     integer,
    PRIMARY KEY (`wishlist_id`, `item_id`),
    FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `item_sizes`
(
    `size_id` integer,
    `item_id` integer,
    PRIMARY KEY (`size_id`, `item_id`),
    FOREIGN KEY (`size_id`) REFERENCES `size` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `available_colors`
(
    `id`      integer PRIMARY KEY AUTO_INCREMENT,
    `color`   enum ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `item_id` integer,
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);