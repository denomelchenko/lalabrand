drop database lalabrand;
create database lalabrand;

use lalabrand;

CREATE TABLE `user`
(
    `id`         integer PRIMARY KEY auto_increment,
    `username`   varchar(50)        NOT NULL,
    `first_name` varchar(50)        NOT NULL,
    `last_name`  varchar(50)        NOT NULL,
    `email`      varchar(50) UNIQUE NOT NULL,
    `phone`      varchar(15),
    `language`   enum ('UA', 'EN'),
    `created_at` timestamp
);

CREATE TABLE `address`
(
    `id`       integer PRIMARY KEY auto_increment,
    `country`  enum ('UA','PL','DE','US','UK') NOT NULL,
    `zip`      varchar(10)                     NOT NULL,
    `city`     varchar(50)                     NOT NULL,
    `address1` varchar(50)                     NOT NULL,
    `address2` varchar(50)                     NOT NULL,
    `user_id`  integer                         NOT NULL
);

CREATE TABLE `shipping_info`
(
    `id`       integer PRIMARY KEY auto_increment,
    `price`    decimal                         NOT NULL,
    `name`     varchar(50)                     NOT NULL,
    `country`  enum ('UA','PL','DE','US','UK') NOT NULL,
    `zip`      varchar(10)                     NOT NULL,
    `city`     varchar(50)                     NOT NULL,
    `address1` varchar(50)                     NOT NULL,
    `address2` varchar(50)                     NOT NULL,
    `user_id`  integer                         NOT NULL
);

CREATE TABLE `roles`
(
    `id`   integer PRIMARY KEY auto_increment,
    `role` enum ('Admin', 'User')
);

CREATE TABLE `users_roles`
(
    `role_id` integer,
    `user_id` integer,
    PRIMARY KEY (`role_id`, `user_id`)
);

CREATE TABLE `wishlist`
(
    `id`      integer PRIMARY KEY auto_increment,
    `user_id` integer NOT NULL
);

CREATE TABLE `wishlist_item`
(
    `wishlist_id` integer,
    `item_id`     integer,
    PRIMARY KEY (`wishlist_id`, `item_id`)
);

CREATE TABLE `cart`
(
    `id`      integer PRIMARY KEY auto_increment,
    `user_id` integer NOT NULL
);

CREATE TABLE `cart_item`
(
    `id`           integer primary key auto_increment,
    `cart_id`      integer,
    `item_id`      integer,
    `item_info_id` integer,
    `size_id`      integer,
    `count`        integer
);

CREATE TABLE `ordered_item`
(
    `id`       integer PRIMARY KEY auto_increment,
    `item_id`  integer      NOT NULL,
    `order_id` integer      NOT NULL,
    `title`    varchar(255) NOT NULL,
    `size`     varchar(255) NOT NULL,
    `color`    varchar(255) NOT NULL,
    `price`    decimal      NOT NULL,
    `count`    integer      NOT NULL,
    `image`    varchar(255) NOT NULL
);

CREATE TABLE `item_info`
(
    `color`   enum ('WHITE', 'BLACK', 'GREY', 'YELLOW', 'RED', 'BLUE', 'GREEN', 'BROWN', 'PINK', 'ORANGE', 'PURPLE') NOT NULL,
    `image`   varchar(255),
    `item_id` integer PRIMARY KEY
);

CREATE TABLE `item`
(
    `id`              integer PRIMARY KEY auto_increment,
    `title`           varchar(40)              NOT NULL,
    `short_disc`      varchar(128)             NOT NULL,
    `long_disc`       varchar(255),
    `rating`          decimal,
    `price`           decimal                  NOT NULL,
    `currency`        enum ('UAH','EUR','USD') NOT NULL,
    `category_id`     integer                  NOT NULL,
    `sold_count`      integer                  NOT NULL,
    `available_count` integer                  NOT NULL,
    `sale_price`      decimal                  NOT NULL,
    `image`           varchar(255)             NOT NULL,
    `created_at`      timestamp                NOT NULL
);

CREATE TABLE `size`
(
    `id`    integer PRIMARY KEY auto_increment,
    `type`  enum ('SHOES','CLOTHES') NOT NULL,
    `value` varchar(40)              NOT NULL
);

CREATE TABLE `items_sizes`
(
    `size_id` integer,
    `item_id` integer,
    PRIMARY KEY (`size_id`, `item_id`)
);
CREATE TABLE `color`
(
    `id`    integer PRIMARY KEY auto_increment,
    `color` enum ('White', 'Black', 'Grey', 'Yellow', 'Red', 'Blue', 'Green', 'Brown', 'Pink', 'Orange', 'Purple') NOT NULL
);

CREATE TABLE `items_colors`
(
    `color_id` integer,
    `item_id`  integer,
    PRIMARY KEY (`color_id`, `item_id`)
);

CREATE TABLE `order`
(
    `id`           integer PRIMARY KEY auto_increment,
    `user_id`      integer,
    `total_price`  decimal,
    `shipping_fee` decimal,
    `discount`     decimal,
    `tax`          decimal,
    `shipping_id`  integer,
    `currency`     enum ('UAH','EUR','USD'),
    `created_at`   timestamp
);

CREATE TABLE `item_comment`
(
    `id`         integer PRIMARY KEY auto_increment,
    `user_id`    integer,
    `item_id`    integer,
    `text`       text      NOT NULL,
    `rating`     decimal   NOT NULL,
    `created_at` timestamp NOT NULL
);

CREATE TABLE `category`
(
    `id`   integer PRIMARY KEY auto_increment,
    `name` varchar(50) UNIQUE
);

CREATE TABLE `look`
(
    `id`     integer PRIMARY KEY auto_increment,
    `gender` enum ('MAN', 'WOMAN', 'UNISEX') NOT NULL,
    `image`  varchar(255)                    NOT NULL
);

CREATE TABLE `look_item`
(
    `look_id` integer,
    `item_id` integer,
    PRIMARY KEY (`look_id`, `item_id`)
);
ALTER TABLE `item_comment`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `item`
    ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `order`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `item_comment`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `address`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `ordered_item`
    ADD FOREIGN KEY (`order_id`) REFERENCES `order` (`id`);

ALTER TABLE `ordered_item`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `wishlist`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `cart`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `item_info`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `wishlist_item`
    ADD FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`);

ALTER TABLE `wishlist_item`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `cart_item`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `cart_item`
    ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);

ALTER TABLE `look_item`
    ADD FOREIGN KEY (`look_id`) REFERENCES `look` (`id`);

ALTER TABLE `look_item`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `order`
    ADD FOREIGN KEY (`shipping_id`) REFERENCES `shipping_info` (`id`);

ALTER TABLE `cart_item`
    ADD FOREIGN KEY (`size_id`) REFERENCES `size` (`id`);

ALTER TABLE `cart_item`
    ADD FOREIGN KEY (`item_info_id`) REFERENCES `item_info` (`item_id`);
