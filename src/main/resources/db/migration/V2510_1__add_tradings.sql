CREATE TABLE la_tradings (
	id bigserial primary key,
	stock_id bigint not null,
	account_id bigint not null,
	trading_type smallint not null,
	quantity float8 not null,
	memo varchar(256) null,
	trade_at timestamp not null
);