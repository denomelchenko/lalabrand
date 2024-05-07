-- Insert statements for the 'user' table
INSERT INTO `user` (`id`, `first_name`, `last_name`, `bonus`, `email`, `phone`, `language`, `password`,
                    `password_version`, `created_at`)
VALUES ('1', 'John', 'Doe', 100, 'john.doe@example.com', '+123456789', 'EN', 'password_hash', 1, CURRENT_TIMESTAMP),
       ('2', 'Alice', 'Smith', 50, 'alice.smith@example.com', '+987654321', 'UA', 'password_hash', 1,
        CURRENT_TIMESTAMP);

-- Insert statements for the 'category' table
INSERT INTO `category` (`id`, `name`)
VALUES ('1', 'Clothing'),
       ('2', 'Electronics'),
       ('3', 'Accessories');

-- Insert statements for the 'item' table
INSERT INTO `item` (`id`, `title`, `short_disc`, `long_disc`, `rating`, `price`, `sold_count`, `category_id`,
                    `available_count`, `sale_price`, `image`, `created_at`)
VALUES ('1', 'T-Shirt', 'Comfortable cotton t-shirt', NULL, 4.5, 19.99, 100, '1', 500, NULL, 'tshirt.jpg',
        CURRENT_TIMESTAMP),
       ('2', 'Hoodie', 'Latest smartphone model', 'Printed with...', 4.8, 799.99, 50, '2', 100, NULL,
        'hoodie.jpg', CURRENT_TIMESTAMP);

-- Insert statements for the 'address' table
INSERT INTO `address` (`id`, `country`, `zip`, `city`, `address1`, `address2`, `user_id`)
VALUES ('1', 'US', '12345', 'New York', '123 Main St', 'Apt 101', '1'),
       ('2', 'UK', 'SW1A 1AA', 'London', '10 Downing St', '', '2');

-- Insert statements for the 'user_roles' table
INSERT INTO `user_roles` (`id`, `role`, `user_id`)
VALUES ('1', 'ADMIN', '1'),
       ('2', 'USER', '2');

-- Insert statements for the 'cart' table
INSERT INTO `cart` (`id`, `user_id`)
VALUES ('1', '1'),
       ('2', '2');

-- Insert statements for the 'item_info' table
INSERT INTO `item_info` (`id`, `color`, `image`, `item_id`, `is_color_available`)
VALUES ('1', 'BLACK', 'tshirt_black.jpg', '1', 1),
       ('2', 'WHITE', 'tshirt_white.jpg', '1', 1),
       ('3', 'GREY', 'tshirt_grey.jpg', '1', 1),
       ('4', 'BLACK', 'smartphone_black.jpg', '2', 1),
       ('5', 'WHITE', 'smartphone_white.jpg', '2', 1);

-- Insert statements for the 'size' table
INSERT INTO `size` (`id`, `type`, `value`)
VALUES ('1', 'CLOTHES', 'S'),
       ('2', 'SHOES', '41');

-- Insert statements for the 'cart_item' table
INSERT INTO `cart_item` (`id`, `cart_id`, `item_id`, `item_info_id`, `size_id`, `count`)
VALUES ('1', '1', '1', '1', '1', 2),
       ('2', '1', '1', '2', '2', 1),
       ('3', '2', '2', '4', '1', 1);

INSERT INTO `shipping_option` (`id`, `name`, `price`)
VALUES ('1', 'NovaPoshta', 100),
       ('2', 'UkrPoshta', 200),
       ('3', 'GlobalPoshta', 150);

-- Insert statements for the 'shipping_info' table
INSERT INTO `shipping_info` (`id`, `country`, `zip`, `city`, `address1`, `address2`, `phone`, `shipping_option_id`)
VALUES ('1', 'US', '12345', 'New York', '123 Main St', 'Apt 101', '+123456789', 1),
       ('2', 'UK', 'SW1A 1AA', 'London', '10 Downing St', '', '+987654321', 2);

-- Insert statements for the 'order' table
INSERT INTO `orders` (`id`, `user_id`, `order_number`, `total_price`, `discount`, `tax`, `shipping_id`, `currency`,
                      `status`, `created_at`)
VALUES ('1', '1', '983129083901', 59.97, NULL, NULL, '1', 'USD', 'PENDING', CURRENT_TIMESTAMP),
       ('2', '2', '983129321342', 799.99, NULL, NULL, '2', 'UAH', 'DELIVERED', CURRENT_TIMESTAMP);


-- Insert statements for the 'ordered_item' table
INSERT INTO ordered_item (id, order_id, item_id, title, size, color, price, count, image)
VALUES ('1', '1', '1', 'T-Shirt', 'CLOTHES', 'BLACK', 19.99, 2, 'tshirt_black.jpg'),
       ('2', '1', '2', 'Running Shoes', 'SHOES', 'BLACK', 79.99, 1, 'runningshoes_black.jpg');


-- Insert statements for the 'item_comment' table
INSERT INTO `item_comment` (`id`, `user_id`, `item_id`, `text`, `rating`, `created_at`)
VALUES ('1', '1', '1', 'Great quality shirt!', 5, CURRENT_TIMESTAMP),
       ('2', '2', '2', 'Love this phone!', 5, CURRENT_TIMESTAMP);

-- Insert statements for the 'look' table
INSERT INTO `look` (`id`, `gender`, `image`, `created_at`)
VALUES ('1', 'MAN', 'man_look.jpg', CURRENT_TIMESTAMP),
       ('2', 'WOMAN', 'woman_look.jpg', CURRENT_TIMESTAMP);

-- Insert statements for the 'look_item' table
INSERT INTO `look_item` (`look_id`, `item_id`)
VALUES ('1', '1'),
       ('2', '2');

-- Insert statements for the 'wishlist' table
INSERT INTO `wishlist` (`id`, `user_id`)
VALUES ('1', '1'),
       ('2', '2');

-- Insert statements for the 'wishlist_item' table
INSERT INTO `wishlist_item` (`wishlist_id`, `item_id`)
VALUES ('1', '1'),
       ('2', '2');

-- Insert statements for the 'item_sizes' table
INSERT INTO `item_sizes` (`size_id`, `item_id`)
VALUES ('1', '1'),
       ('2', '1'),
       ('1', '2');

-- Insert statements for the 'refresh_token' table
INSERT INTO `refresh_token` (`token`, `expires_at`, `user_id`)
VALUES ('token1', '2024-04-16 12:00:00', '1'),
       ('token2', '2024-04-16 12:00:00', '2');

-- Insert statements for the 'password_reset_token' table
INSERT INTO `password_reset_token` (`id`, `token`, `expires_at`, `user_id`)
VALUES ('1', 'token123', '2024-04-16 12:00:00', '1'),
       ('2', 'token456', '2024-04-16 12:00:00', '2');
