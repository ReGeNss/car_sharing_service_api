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

-- 3. Оновити тариф (ціна, якщо пробіг <20)
SELECT * FROM tariff WHERE tariff_id = 2 AND included_mileage < 20;
UPDATE tariff
SET price_per_minute = 8.00
WHERE tariff_id = 2 AND included_mileage < 20;
SELECT * FROM tariff WHERE tariff_id = 2 AND included_mileage < 20;

-- 4. Проставити завершення бронювання
SELECT * FROM booking WHERE booking_id = 1;
UPDATE booking
SET status = 'expired',
    end_time = NOW()
WHERE booking_id = 1;
SELECT * FROM booking WHERE booking_id = 1;
