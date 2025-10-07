ALTER TABLE la_tradings
ALTER COLUMN market_current_price DROP NOT NULL;
ALTER TABLE la_tradings
RENAME COLUMN market_current_price TO price;