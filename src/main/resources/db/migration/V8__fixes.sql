ALTER TABLE `order`
    ADD CONSTRAINT uc_order_shipping UNIQUE (shipping_id),
    MODIFY shipping_fee DECIMAL(10) NOT NULL;

ALTER TABLE refresh_token
    ADD CONSTRAINT uc_refresh_token_user UNIQUE (user_id);

ALTER TABLE address
    MODIFY address1 VARCHAR(50) NOT NULL,
    MODIFY address2 VARCHAR(50) NOT NULL,
    MODIFY city VARCHAR(50) NOT NULL,
    MODIFY zip VARCHAR(10) NOT NULL;

ALTER TABLE shipping_info
    MODIFY address2 VARCHAR(50) NOT NULL,
    MODIFY address1 VARCHAR(50) NOT NULL,
    MODIFY city VARCHAR(50) NOT NULL,
    MODIFY zip VARCHAR(10) NOT NULL,
    MODIFY phone VARCHAR(15) NOT NULL;

ALTER TABLE category
    MODIFY name VARCHAR(50) NOT NULL;

ALTER TABLE user
    MODIFY phone VARCHAR(15) NOT NULL;

ALTER TABLE shipping_option
    MODIFY price DECIMAL(10) NOT NULL;

ALTER TABLE item
    MODIFY short_disc VARCHAR(128) NOT NULL;

ALTER TABLE item
    MODIFY title VARCHAR(40) NOT NULL;