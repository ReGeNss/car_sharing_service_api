INSERT INTO coordinates (latitude, longitude)
VALUES
    (50.420000, 30.500000),
    (50.450000, 30.550000),
    (50.480000, 30.420000);

INSERT INTO users (first_name, last_name, email, password, phone, passport_data, driver_license, registration_date, status)
VALUES
    ('Ivan',  'Petrenko',  'ivan@example.com',  'pass123', '+380501112233', 'PP123456', 'DL987654', NOW(), 'active'),
    ('Maria', 'Kovalenko', 'maria@example.com', 'qwerty',  '+380507778899', 'PP654321', 'DL555444', NOW(), 'active'),
    ('Oleh',  'Sydorenko', 'oleh@example.com',  'pass321', '+380503334455', 'PP111222', 'DL222111', NOW(), 'blocked');

INSERT INTO tariff (name, price_per_minute, included_mileage, booking_price, booking_duration_minutes, deposit, insurance)
VALUES
    ('Standard', 5.00, 10, 20.00, 15, 200.00, 50.00),
    ('Comfort',  7.50, 15, 25.00, 20, 250.00, 60.00),
    ('Premium', 10.00, 20, 30.00, 30, 300.00, 80.00);

INSERT INTO vehicle (brand, model, plate_number, vin, type, status, location, fuel_level, tariff_id)
VALUES
    ('Tesla',   'Model 3',  'AA1234BB', '1HGCM82633A004352', 'electric', 'available',   1, 90, 1),
    ('Toyota',  'Corolla',  'BC5678CD', '2HGCM82633A004353', 'petrol',   'maintenance', 2, 60, 2),
    ('Hyundai', 'Ioniq',    'AE1111CE', '3HGCM82633A004354', 'hybrid',   'booked',      3, 75, 1);

INSERT INTO booking (user_id, vehicle_id, start_time, status)
VALUES
    (1, 1, NOW(), 'active'),
    (2, 2, NOW(), 'active'),
    (3, 3, NOW(), 'expired');

INSERT INTO trip (user_id, vehicle_id, start_time, start_location, distance, cost)
VALUES
    (1, 1, NOW(), 1,  5.50, 27.50),
    (2, 2, NOW(), 2, 10.00, 50.00),
    (3, 3, NOW(), 3,  2.00, 10.00);
