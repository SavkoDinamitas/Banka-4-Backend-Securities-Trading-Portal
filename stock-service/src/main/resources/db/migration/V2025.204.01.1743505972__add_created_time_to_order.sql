ALTER TABLE orders
    ADD COLUMN created_at TIMESTAMP WITH TIME ZONE;
UPDATE orders
SET created_at = last_modified;
ALTER TABLE orders
    ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE orders
    ALTER COLUMN created_at SET DEFAULT now();
