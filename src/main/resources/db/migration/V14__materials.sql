CREATE TABLE `materials` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `items_materials`
(
    `item`        varchar(36) NOT NULL,
    `material_id` int(11)     NOT NULL,
    `percentage` INT CHECK (percentage > 0 AND percentage <= 100),
    PRIMARY KEY (`item`, `material_id`),
    FOREIGN KEY (`item`) REFERENCES `item` (`id`),
    FOREIGN KEY (`material_id`) REFERENCES `materials` (`id`)
);
