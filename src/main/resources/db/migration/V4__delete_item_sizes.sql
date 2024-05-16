ALTER TABLE `item_info`
    ADD COLUMN `size_id` VARCHAR(36) NOT NULL,
    ADD FOREIGN KEY (`size_id`) REFERENCES size(`id`);
DROP TABLE `item_sizes`;
