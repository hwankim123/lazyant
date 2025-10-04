CREATE TABLE la_stocks (
	id bigserial PRIMARY KEY,
	name varchar(256) NULL,
	market varchar(16) NULL,
	symbol varchar(16) NULL,
	etf_type varchar(64) NULL,
	type varchar(16) NULL,
);