SELECT SUM(amount) AS total_penalty_amount
FROM penalty;

SELECT AVG(mileage) AS average_maintenance_mileage
FROM maintenance;

SELECT booking_price, COUNT(*) AS tariff_count
FROM tariff
WHERE deposit > 1000
GROUP BY booking_price;


SELECT MIN(price_per_minute) AS min_tariff_price
FROM tariff;