SELECT 
    v.brand, 
    v.model, 
    SUM(t.cost) AS total_revenue
FROM vehicle v
JOIN trip t ON v.vehicle_id = t.vehicle_id
GROUP BY v.brand, v.model
ORDER BY total_revenue DESC;

SELECT 
    u.first_name, 
    u.last_name, 
    SUM(p.amount) AS total_spent
FROM users u
JOIN payment p ON u.user_id = p.user_id
WHERE p.status = 'paid'
GROUP BY u.user_id, u.first_name, u.last_name;

SELECT
   u.email,
   SUM(t.distance) AS total_kilometers
FROM users u
JOIN trip t ON u.user_id = t.user_id
GROUP BY u.email
ORDER BY total_kilometers DESC;


SELECT
   tr.name AS tariff_name,
   COUNT(b.booking_id) AS total_bookings
FROM booking b
JOIN vehicle v ON b.vehicle_id = v.vehicle_id
JOIN tariff tr ON v.tariff_id = tr.tariff_id
GROUP BY tr.name;

SELECT
   tr.name AS tariff_name,
   AVG(t.cost) AS average_trip_cost
FROM trip t
JOIN vehicle v ON t.vehicle_id = v.vehicle_id
JOIN tariff tr ON v.tariff_id = tr.tariff_id
GROUP BY tr.name;

SELECT
   v.brand,
   SUM(p.amount) AS total_penalty_amount
FROM penalty p
JOIN trip t ON p.trip_id = t.trip_id
JOIN vehicle v ON t.vehicle_id = v.vehicle_id
GROUP BY v.brand;
