CREATE TABLE `materials`
(
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`name`)
);

CREATE TABLE `items_materials`
(
    `item_id`             varchar(36) NOT NULL,
    `material_name` varchar(30) NOT NULL,
    `percentage`       INT CHECK (percentage > 0 AND percentage <= 100),
    PRIMARY KEY (`item_id`, `material_name`),
    FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
    FOREIGN KEY (`material_name`) REFERENCES `materials` (`name`)
);
