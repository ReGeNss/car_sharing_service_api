INSERT INTO users (
    first_name, last_name, email, password, phone,
    passport_data, driver_license, registration_date, status
) VALUES
('Ivan', 'Petrenko', 'ivan.petrenko@example.com', 'hash1',
 '+380501112233', 'AB123456', '12345678', NOW(), 'active'),

('Oleh', 'Shevchenko', 'oleh.shevchenko@example.com', 'hash2',
 '+380671234567', 'CD234567', '23456789', NOW(), 'active'),

('Marta', 'Koval', 'marta.koval@example.com', 'hash3',
 '+380931112233', 'EF345678', '34567890', NOW(), 'blocked'),

('Andriy', 'Bondar', 'andriy.bondar@example.com', 'hash4',
 '+380931234567', 'GH456789', '45678901', NOW(), 'active'),

('Sofia', 'Melnyk', 'sofia.melnyk@example.com', 'hash5',
 '+380501234999', 'IJ567890', '56789012', NOW(), 'active'),

('Taras', 'Lysenko', 'taras.lysenko@example.com', 'hash6',
 '+380671119988', 'KL678901', '67890123', NOW(), 'active'),

('Nazar', 'Horobets', 'nazar.horobets@example.com', 'hash7',
 '+380931224466', 'MN789012', '78901234', NOW(), 'active');
