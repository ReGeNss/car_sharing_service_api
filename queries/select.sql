-- 1. Знайти всі доступні машини

SELECT * FROM vehicle WHERE status = 'available';

-- 2. Знайти всіх активних користувачів

SELECT * FROM users WHERE status = 'active';

-- 3. Знайти всі тарифи, вартість яких > 6

SELECT * FROM tariff WHERE price_per_minute > 6;

-- 4. Знайти всі бронювання всіх активних користувачів

SELECT * FROM booking WHERE user_id IN (SELECT user_id FROM users WHERE status = 'active');
