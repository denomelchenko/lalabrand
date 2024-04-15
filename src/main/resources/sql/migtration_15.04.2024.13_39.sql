ALTER TABLE `cart_item`
    ADD CONSTRAINT `unique_cart_item_combination` UNIQUE (`cart_id`, `size_id`, `item_info_id`, `item_id`);
