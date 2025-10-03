CREATE TABLE la_tradings (
	id bigserial primary key,
	stock_id bigserial not null,
	account_id bigserial not null,
	trading_type varchar(64) not null,
	current_market_price float8 not null,
	volume float8 not null,
	memo varchar(256),
	trade_time timestamp not null
);