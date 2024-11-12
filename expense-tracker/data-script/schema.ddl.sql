DROP TABLE IF EXISTS account_custom_expense_categories CASCADE; 
DROP TABLE IF EXISTS expenses CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;

CREATE TABLE accounts (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	password VARCHAR(100) NOT NULL
);


CREATE TABLE expenses (
	id SERIAL PRIMARY KEY,
	account_id INTEGER NOT NULL REFERENCES accounts(id),
	amount NUMERIC(7, 2) NOT NULL,
	description VARCHAR(200),
	category_name VARCHAR(100) NOT NULL,  -- an enum value or custom category name
	added_at DATE NOT NULL
);


CREATE TABLE account_custom_expense_categories (
	id SERIAL PRIMARY KEY,
	category_name VARCHAR(100) UNIQUE NOT NULL,
	account_id INTEGER NOT NULL REFERENCES accounts(id)
);