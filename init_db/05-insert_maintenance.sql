INSERT INTO maintenance (vehicle_id, type, date, mileage, comment, status) VALUES
(4, 'Engine Check', NOW() - INTERVAL '7 days', 120000, 'Oil change', 'done'),
(2, 'Brake Check', NOW() - INTERVAL '2 days', 90000, 'Pads replaced', 'done'),
(3, 'Battery Inspection', NOW() - INTERVAL '14 days', 50000, 'OK', 'planned'),
(6, 'Full Inspection', NOW() - INTERVAL '1 month', 40000, 'Scheduled', 'planned'),
(1, 'Tire Change', NOW() - INTERVAL '20 days', 70000, 'Front tires replaced', 'done');
