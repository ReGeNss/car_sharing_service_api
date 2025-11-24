SELECT 
    u.last_name, 
    p.amount, 
    p.type AS penalty_reason, 
    v.plate_number
FROM penalty p
INNER JOIN users u ON p.user_id = u.user_id
INNER JOIN trip t ON p.trip_id = t.trip_id
INNER JOIN vehicle v ON t.vehicle_id = v.vehicle_id;

SELECT 
    u.first_name, 
    u.last_name, 
    b.booking_id, 
    b.start_time
FROM users u
LEFT JOIN booking b ON u.user_id = b.user_id;

SELECT
   v.plate_number,
   c.latitude,
   c.longitude
FROM vehicle v
JOIN coordinates c ON v.location = c.coordinates_id;


SELECT
   v.brand,
   m.date,
   m.type,
   m.comment
FROM vehicle v
RIGHT JOIN maintenance m ON v.vehicle_id = m.vehicle_id;
