CREATE TYPE user_status AS ENUM ('active', 'blocked');

CREATE TYPE vehicle_type AS ENUM ('electric','petrol','hybrid');

CREATE TYPE vehicle_status AS ENUM ('available','booked','in_trip','maintenance');

CREATE TYPE booking_status AS ENUM ('active','expired','cancelled');

CREATE TYPE payment_method AS ENUM ('card','cash');

CREATE TYPE payment_status AS ENUM ('pending','paid','failed');

CREATE TYPE maintenance_status AS ENUM ('planned','done');

CREATE TABLE coordinates (
    coordinates_id SERIAL PRIMARY KEY,
    latitude DECIMAL(9,6),
    longitude DECIMAL(9,6),
    
    CONSTRAINT check_in_rect CHECK (
        latitude BETWEEN 50.40 AND 50.50 AND
        longitude BETWEEN 30.40 AND 30.60
    )
);

CREATE TABLE tariff (
    tariff_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price_per_minute NUMERIC(10,2) NOT NULL CHECK (price_per_minute >= 0),
    included_mileage INT NOT NULL CHECK (included_mileage >= 0),
    booking_price NUMERIC(10,2) NOT NULL CHECK (booking_price >= 0),
    booking_duration_minutes INT NOT NULL CHECK (booking_duration_minutes > 0),
    deposit NUMERIC(10,2) NOT NULL CHECK (deposit >= 0),
    insurance NUMERIC(10,2) NOT NULL CHECK (insurance >= 0)
);
