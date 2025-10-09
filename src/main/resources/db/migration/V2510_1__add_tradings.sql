CREATE TABLE la_tradings (
	id bigserial primary key,
	stock_id bigint not null,
	account_id bigint not null,
	trading_type smallint not null,
	market_current_price float8 not null,
	volume float8 not null,
	memo varchar(256) null,
	trade_time timestamp not null
);