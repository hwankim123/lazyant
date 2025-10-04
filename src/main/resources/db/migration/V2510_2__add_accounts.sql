CREATE TABLE la_accounts (
	id bigserial primary key,
	name varchar(128) not null,
	nickname varchar(128) null,
	bank smallint not null
);
