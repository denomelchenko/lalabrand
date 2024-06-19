ALTER TABLE address
    MODIFY country ENUM ('UA', 'PL', 'DE', 'US', 'UK') NOT NULL;

ALTER TABLE shipping_info
    MODIFY country ENUM ('UA', 'PL', 'DE', 'US', 'UK') NOT NULL;