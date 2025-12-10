CREATE TYPE user_status AS ENUM ('active', 'inactive', 'blocked');
CREATE TYPE vehicle_type AS ENUM ('electric','petrol','hybrid');
CREATE TYPE vehicle_status AS ENUM ('available','booked','in_trip','maintenance');
CREATE TYPE booking_status AS ENUM ('active','expired','cancelled');
CREATE TYPE payment_method AS ENUM ('card','cash');
CREATE TYPE payment_status AS ENUM ('pending','paid','failed');
CREATE TYPE maintenance_status AS ENUM ('planned','done');

CREATE TABLE coordinates (
    coordinates_id SERIAL PRIMARY KEY,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    
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

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    password TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL UNIQUE CHECK (phone ~ '^\+380[0-9]{9}$'),
    passport_data VARCHAR(255) NOT NULL UNIQUE CHECK (passport_data ~ '^[A-Z]{2}[0-9]{6}$'),
    driver_license VARCHAR(255) NOT NULL UNIQUE CHECK (driver_license ~ '^[0-9]{8}$'),
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status user_status NOT NULL
);

CREATE TABLE vehicle_model (
    model_id SERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model_name VARCHAR(100) NOT NULL,
    type vehicle_type NOT NULL, -- Type is usually inherent to the model
    CONSTRAINT uq_brand_model UNIQUE (brand, model_name)
);

CREATE TABLE vehicle (
    vehicle_id SERIAL PRIMARY KEY,
    model_id INT NOT NULL REFERENCES vehicle_model(model_id), -- Replaces brand, model, type
    plate_number VARCHAR(8) NOT NULL UNIQUE CHECK (plate_number ~ '^[A-Z]{2}[0-9]{4}[A-Z]{2}$'),
    vin VARCHAR(17) NOT NULL UNIQUE CHECK (vin ~ '^[A-HJ-NPR-Z0-9]{17}$'),
    status vehicle_status NOT NULL,
    location INT NOT NULL REFERENCES coordinates(coordinates_id),
    fuel_level INT CHECK (fuel_level BETWEEN 0 AND 100),
    tariff_id INT REFERENCES tariff(tariff_id)
);

CREATE TABLE booking (
    booking_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(user_id),
    vehicle_id INT NOT NULL REFERENCES vehicle(vehicle_id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status booking_status NOT NULL
);

CREATE TABLE trip (
    trip_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(user_id),
    vehicle_id INT NOT NULL REFERENCES vehicle(vehicle_id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    start_location INT NOT NULL REFERENCES coordinates(coordinates_id),
    end_location INT REFERENCES coordinates(coordinates_id),
    distance NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (distance >= 0),
    cost NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (cost >= 0)
);

CREATE TABLE payment (
    payment_id SERIAL PRIMARY KEY,
    trip_id INT REFERENCES trip(trip_id),
    booking_id INT NOT NULL REFERENCES booking(booking_id),
    amount NUMERIC(10,2) NOT NULL CHECK (amount >= 0),
    method payment_method NOT NULL,
    status payment_status NOT NULL
);

CREATE TABLE maintenance (
    maintenance_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL REFERENCES vehicle(vehicle_id),
    type VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    mileage NUMERIC(10,2) NOT NULL CHECK (mileage >= 0),
    comment VARCHAR(255),
    status maintenance_status NOT NULL
);

CREATE TABLE penalty (
    penalty_id SERIAL PRIMARY KEY,
    trip_id INT NOT NULL REFERENCES trip(trip_id),
    type VARCHAR(150) NOT NULL,
    amount NUMERIC(10,2) NOT NULL CHECK (amount >= 0),
    date TIMESTAMP NOT NULL
);
