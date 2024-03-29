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
    `created_at` timestamp
);

CREATE TABLE `address`
(
    `id`       integer PRIMARY KEY auto_increment,
    `country`  enum ("UA","PL","DE","US","UK") NOT NULL,
    `zip`      varchar(10)                     NOT NULL,
    `city`     varchar(50)                     NOT NULL,
    `address1` varchar(50)                     NOT NULL,
    `address2` varchar(50)                     NOT NULL,
    `user_id`  integer                         NOT NULL
);

CREATE TABLE `role`
(
    `id`   integer PRIMARY KEY auto_increment,
    `role` enum ('admin', 'user')
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

CREATE TABLE `wishlist_record`
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

CREATE TABLE `cart_record`
(
    `cart_id` integer,
    `item_id` integer,
    PRIMARY KEY (`cart_id`, `item_id`)
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

CREATE TABLE `item`
(
    `id`              integer PRIMARY KEY auto_increment,
    `title`           varchar(40)              NOT NULL,
    `short_disc`      varchar(128)             NOT NULL,
    `long_disc`       varchar(255),
    `rating`          decimal,
    `price`           decimal                  NOT NULL,
    `currency`        enum ("UAH","EUR","USD") NOT NULL,
    `category_id`     integer                  NOT NULL,
    `sold_count`      integer                  NOT NULL,
    `available_count` integer                  NOT NULL,
    `sale_price`      integer                  NOT NULL,
    `image`           varchar(255)             NOT NULL,
    `created_at`      timestamp                NOT NULL
);

CREATE TABLE `size`
(
    `id`    integer PRIMARY KEY auto_increment,
    `type`  enum ("shoes","clothes") NOT NULL,
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
    `color` enum ("White", "Black", "Grey", "Yellow", "Red", "Blue", "Green", "Brown", "Pink", "Orange", "Purple") NOT NULL
);

CREATE TABLE `items_colors`
(
    `color_id` integer,
    `item_id`  integer,
    PRIMARY KEY (`color_id`, `item_id`)
);

CREATE TABLE `order`
(
    `id`         integer PRIMARY KEY auto_increment,
    `user_id`    integer   NOT NULL,
    `created_at` timestamp NOT NULL
);

CREATE TABLE `item_comment`
(
    `id`         integer PRIMARY KEY auto_increment,
    `user_id`    integer,
    `item_id`    integer,
    `text`       text      NOT NULL,
    `raiting`    decimal   NOT NULL,
    `created_at` timestamp NOT NULL
);

CREATE TABLE `category`
(
    `id`   integer PRIMARY KEY auto_increment,
    `name` varchar(50) UNIQUE
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

ALTER TABLE `users_roles`
    ADD FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `users_roles`
    ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `items_colors`
    ADD FOREIGN KEY (`color_id`) REFERENCES `color` (`id`);

ALTER TABLE `items_colors`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `items_sizes`
    ADD FOREIGN KEY (`size_id`) REFERENCES `size` (`id`);

ALTER TABLE `items_sizes`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `wishlist_record`
    ADD FOREIGN KEY (`wishlist_id`) REFERENCES `wishlist` (`id`);

ALTER TABLE `wishlist_record`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `cart_record`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

ALTER TABLE `cart_record`
    ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`);
