-- Стартовые пользователи
-- 356a192b7913b04c54574d18c28d46e6395428ab - sha1 хэш пароля "1"
INSERT INTO accounts (first_name, second_name, email, phone_number, passhash, settings_notification, schedule_notidication) VALUES
  ('Alex', 'Smith', 'a@a.aa', NULL, '356a192b7913b04c54574d18c28d46e6395428ab', TRUE, TRUE ),
  ('Benjamin', 'Rogers', NULL, 9307090399, '356a192b7913b04c54574d18c28d46e6395428ab', TRUE, TRUE ),
  ('Charles', 'Clarkson', 'b@b.bb', NULL, '356a192b7913b04c54574d18c28d46e6395428ab', TRUE, TRUE );

INSERT INTO groups (name, leader_id) VALUES
  ('Первая группа', 2),
  ('Вторая группа', 1);

UPDATE accounts
SET group_id = 2
WHERE id = 1;

UPDATE accounts
SET group_id = 1
WHERE id = 2;

UPDATE accounts
SET group_id = 1
WHERE id = 3;

INSERT INTO lessons (name, room, start_datetime, end_datetime, teacher, group_id) VALUES
  ('Java', 1234, TIMESTAMP '2018-02-10 10:30:00', TIMESTAMP '2018-02-10 12:00:00', 'teacher A', 1),
  ('Матпрограммирование', 2345, TIMESTAMP '2018-02-9 10:30:00', TIMESTAMP '2018-02-9 12:00:00', 'teacher B', 1),
  ('Скриптовые языки программирования', 3456, TIMESTAMP '2018-02-9 12:30:00', TIMESTAMP '2018-02-9 15:00:00',
   'teacher C', 1);

INSERT INTO events (name, start_datetime, place, group_id, description) VALUES
  ('Событие 1', TIMESTAMP '2018-02-10 18:00:00', 'пл. Минина и Пожарского', 1, 'описание события 1'),
  ('Событие 2', TIMESTAMP '2018-02-11 18:00:00', 'НГТУ', 1, 'описание события 1');

INSERT INTO tags (name) VALUES
  ('нч'),
  ('чн'),
  ('лабораторная'),
  ('лекция'),
  ('практика'),
  ('упражнения'),
  ('зачет'),
  ('экзамен'),
  ('консультация'),
  ('встреча');

INSERT INTO lesson_tags (lesson_id, tag_id) VALUES
  (1, 3),
  (1, 1),
  (1, 8),
  (2, 4);

INSERT INTO event_tags (event_id, tag_id) VALUES
  (1, 4),
  (1, 10),
  (2, 4),
  (2, 9);