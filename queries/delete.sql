-- 1. Видалення штрафу
SELECT * FROM penalty WHERE penalty_id = 3;
DELETE FROM penalty WHERE penalty_id = 3;
SELECT * FROM penalty WHERE penalty_id = 3;

-- 2. Видалення технічного обслуговування
SELECT * FROM maintenance WHERE maintenance_id = 1;
DELETE FROM maintenance WHERE maintenance_id = 1;
SELECT * FROM maintenance WHERE maintenance_id = 1;