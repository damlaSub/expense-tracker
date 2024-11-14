DELETE FROM accounts;
DELETE FROM expenses;

INSERT INTO accounts
	(first_name, last_name, email, password)
	VALUES
	('Test', 'Test', 'test@test.com', '$2a$11$y.tJxXdq/ee22bshvydE9u9o3w1qqwo1XSaS1nr3HOd9jrZ7PoekC'),
	('Damla', 'Test', 'damla@test.com', '$2a$11$y.tJxXdq/ee22bshvydE9u9o3w1qqwo1XSaS1nr3HOd9jrZ7PoekC');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 100.00, 'Lunch', 'FOOD', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 50.00, 'Yoga', 'SPORT', '2024-11-01');
