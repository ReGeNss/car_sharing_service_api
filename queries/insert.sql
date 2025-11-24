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