SELECT user_id, SUM(amount) AS total_spent
FROM payment
WHERE status = 'paid'
GROUP BY user_id
HAVING SUM(amount) > 2000;

SELECT vehicle_id, COUNT(*) AS trip_count
FROM trip
GROUP BY vehicle_id
HAVING COUNT(*) >= 3;

SELECT user_id, AVG(distance) AS avg_distance
FROM trip
GROUP BY user_id
HAVING AVG(distance) > 10;


SELECT user_id, COUNT(*) AS cancelled_count
FROM booking
WHERE status = 'cancelled'
GROUP BY user_id
HAVING COUNT(*) > 1;

SELECT brand, AVG(fuel_level) AS average_fuel
FROM vehicle
GROUP BY brand
HAVING AVG(fuel_level) < 50;


SELECT type, COUNT(*) AS frequency
FROM maintenance
GROUP BY type
HAVING COUNT(*) > 1;
