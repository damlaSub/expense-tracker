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
VALUES (1, 50.00, 'Yoga', 'PERSONAL_CARE', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 100.00, '', 'GROCERIES', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 5.00, '', 'PERSONAL_CARE', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 8.00, 'Breakfast', 'FOOD', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 50.00, 'Certif', 'EDUCATION', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 22.00, 'Dinner', 'FOOD', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 500.00, '', 'TRAVEL', '2024-11-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 10.00, '', 'FOOD', '2024-10-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 500.00, '', 'TRAVEL', '2024-10-01');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 9.00, 'Sandwich', 'FOOD', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 6.00, '', 'TRAVEL', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 160.00, '', 'ENTERTAINMENT', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 80.00, '', 'UTILITIES', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 38.00, '', 'HEALTH', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 56.00, '', 'GROCERIES', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 16.00, '', 'TRANSPORTATION', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 60.00, '', 'EDUCATION', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 6.00, '', 'PERSONAL_CARE', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 6.00, '', 'SHOPPING', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 60.00, '', 'OTHER', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 40.00, '', 'BILLS', '2024-11-17');

INSERT INTO expenses (account_id, amount, description, category, added_at)
VALUES (1, 500.00, '', 'INVESTMENTS', '2024-11-17');

