INSERT INTO penalty (user_id, trip_id, type, amount, date) VALUES
(1, 1, 'Speeding', 300.00, NOW() - INTERVAL '1 day'),
(2, 2, 'Illegal parking', 500.00, NOW() - INTERVAL '2 days'),
(3, 3, 'Smoking in car', 1000.00, NOW() - INTERVAL '5 days'),
(4, 4, 'Car left dirty', 200.00, NOW() - INTERVAL '1 day'),
(5, 5, 'Late return', 150.00, NOW() - INTERVAL '3 days');
