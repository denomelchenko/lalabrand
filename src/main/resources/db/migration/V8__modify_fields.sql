ALTER TABLE item_comment
    MODIFY text LONGTEXT;

ALTER TABLE ordered_item
    MODIFY size ENUM('SHOES', 'CLOTHES');