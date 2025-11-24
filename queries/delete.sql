-- 1. Видалення штрафу
SELECT * FROM penalty WHERE penalty_id = 3;
DELETE FROM penalty WHERE penalty_id = 3;
SELECT * FROM penalty WHERE penalty_id = 3;

-- 2. Видалення технічного обслуговування
SELECT * FROM maintenance WHERE maintenance_id = 1;
DELETE FROM maintenance WHERE maintenance_id = 1;
SELECT * FROM maintenance WHERE maintenance_id = 1;

-- 3. Видалення failed-платежів
SELECT * FROM payment WHERE status = 'failed';
DELETE FROM payment WHERE status = 'failed';
SELECT * FROM payment WHERE status = 'failed';

-- 4. Видалення прострочених бронювань
SELECT * FROM booking WHERE status = 'expired';
DELETE FROM booking WHERE status = 'expired';
SELECT * FROM booking WHERE status = 'expired';

-- 5. Видалення коротких поїздок
SELECT * FROM trip WHERE distance < 3;
DELETE FROM trip WHERE distance < 3;
SELECT * FROM trip WHERE distance < 3;

-- 6. Видалення заблокованих користувачів
SELECT * FROM users WHERE status = 'blocked';
DELETE FROM users WHERE status = 'blocked';
