-- Стартовые пользователи
-- 356a192b7913b04c54574d18c28d46e6395428ab - sha1 хэш пароля "1"
INSERT INTO accounts (name, first_name, second_name, email, phone_number, passhash) VALUES
('Alex', 'Alex', 'Smith', 'a@a.aa', NULL, '356a192b7913b04c54574d18c28d46e6395428ab'),
('Ben', 'Benjamin', 'Rogers', NULL, 9307090399, '356a192b7913b04c54574d18c28d46e6395428ab'),
('Charlie', 'Charles', 'Clarkson', 'b@b.bb', NULL, '356a192b7913b04c54574d18c28d46e6395428ab');

INSERT INTO groups(name, leader_id) VALUES
  ('Первая группа', 2),
  ('Вторая группа', 1);

INSERT INTO lessons(name, room, start_datetime, end_datetime, teacher, group_id) VALUES
  ('Java', 1234, TIMESTAMP '2018-02-10 10:30:00', TIMESTAMP '2018-02-10 12:00:00', 'teacher A', 1),
  ('Матпрограммирование', 2345, TIMESTAMP '2018-02-9 10:30:00', TIMESTAMP '2018-02-9 12:00:00', 'teacher B', 1),
  ('Скриптовые языки программирования', 3456, TIMESTAMP '2018-02-9 12:30:00', TIMESTAMP '2018-02-9 15:00:00', 'teacher C', 1);

UPDATE accounts
SET group_id = 2
WHERE id = 1;

UPDATE accounts
SET group_id = 1
WHERE id = 2;

UPDATE accounts
SET group_id = 1
WHERE id = 3;