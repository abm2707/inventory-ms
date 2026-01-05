ALTER TABLE inventory_item
ADD COLUMN total_quantity INTEGER;

-- Migrate existing data
UPDATE inventory_item
SET total_quantity = available_quantity;

-- Make it NOT NULL after data is populated
ALTER TABLE inventory_item
ALTER COLUMN total_quantity SET NOT NULL;

-- Optional: drop old columns if no longer needed
