CREATE TABLE la_stock_assets (
	id bigserial PRIMARY KEY,
	stock_id bigint not null,
	volume float8 not null default 0,
	investment_principal float8 not null default 0
);