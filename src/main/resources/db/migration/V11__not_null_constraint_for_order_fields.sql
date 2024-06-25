UPDATE orders
SET discount = IFNULL(discount, 0),
    tax = IFNULL(tax, 0);

ALTER TABLE orders
    MODIFY discount DECIMAL(10) NOT NULL DEFAULT 0;

ALTER TABLE orders
    MODIFY tax DECIMAL(10) NOT NULL DEFAULT 0;