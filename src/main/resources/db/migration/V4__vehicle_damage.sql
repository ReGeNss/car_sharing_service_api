ALTER TABLE users 
ADD COLUMN driver_license_photo_url VARCHAR(500);

ALTER TABLE vehicle 
ALTER COLUMN plate_number TYPE VARCHAR(15);


ALTER TABLE vehicle 
DROP CONSTRAINT vehicle_plate_number_check;

ALTER TABLE vehicle 
ADD CONSTRAINT vehicle_plate_number_check 
CHECK (plate_number ~ '^[A-Z0-9-]{4,15}$');

CREATE TABLE vehicle_damages (
    damage_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL REFERENCES vehicle(vehicle_id),
    photo_url VARCHAR(500) NOT NULL,
    description TEXT,
    reported_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);