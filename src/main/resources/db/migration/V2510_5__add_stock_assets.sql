CREATE TABLE la_stock_assets (
	id bigserial PRIMARY KEY,
	stock_id bigint not null,
	quantity float8 not null default 0,
	principal float8 not null default 0
);