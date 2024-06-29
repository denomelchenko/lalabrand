ALTER TABLE `order`
    RENAME TO `orders`;

ALTER TABLE orders
    ADD order_number BIGINT NOT NULL;

ALTER TABLE orders
    DROP COLUMN shipping_fee;

ALTER TABLE orders
    ADD status ENUM ('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELED') NOT NULL;

