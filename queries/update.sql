-- 1. Змінити статус авто
SELECT * FROM vehicle WHERE vehicle_id = 3;
UPDATE vehicle
SET status = 'available'
WHERE vehicle_id = 3;
SELECT * FROM vehicle WHERE vehicle_id = 3;

-- 2. Оновити номер телефону користувача
SELECT * FROM users WHERE user_id = 1;
UPDATE users
SET phone = '+380509991122'
WHERE user_id = 1;
SELECT * FROM users WHERE user_id = 1;