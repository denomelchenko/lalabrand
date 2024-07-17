ALTER TABLE item ADD COLUMN category_name VARCHAR(36);

UPDATE item i
    JOIN category c ON i.category_id = c.id
SET i.category_name = c.name;


ALTER TABLE item DROP FOREIGN KEY item_ibfk_1;
ALTER TABLE item DROP COLUMN category_id;
ALTER TABLE item
    ADD CONSTRAINT fk_category_name
        FOREIGN KEY (category_name) REFERENCES category(name);

ALTER TABLE category DROP COLUMN id;
ALTER TABLE category ADD PRIMARY KEY (name);