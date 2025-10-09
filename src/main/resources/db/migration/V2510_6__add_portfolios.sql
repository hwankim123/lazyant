CREATE TABLE la_portfolios (
	id bigserial PRIMARY KEY,
	user_id bigserial not null,
	name varchar(256) not null
);

CREATE TABLE la_portfolio_items (
	id bigserial PRIMARY KEY,
	portfolio_id bigserial not null,
	factor smallint not null,
	weight smallint not null
);

ALTER TABLE la_stock_assets add column portfolio_item_id bigint null;
