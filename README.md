# Car Sharing Database Design Report

## 1. Requirements Summary

### Stakeholder Needs
The car-sharing system must support:
- User registration and document verification.
- Viewing available vehicles with detailed information.
- Reserving vehicles for a limited period.
- Starting and ending trips with recorded distance, time, and locations.
- Processing payments for trips, bookings, deposits, and insurance.
- Managing penalties for violations.
- Managing pricing plans (tariffs).
- Recording vehicle maintenance history.

### Data to Store
The system stores:
- User identities and contact information.
- Vehicle specifications and status.
- Bookings and completed trips.
- Payments related to trips or reservations.
- Tariff data with pricing rules.
- Maintenance operations.
- Penalties issued to users.

### Business Rules
- A user may have several bookings but only one active booking at a time.
- A booking may or may not convert into a trip.
- A vehicle can only have one active booking at a time.
- Each vehicle is assigned exactly one tariff.
- A single trip can have multiple penalties linked to it.

## 2. ER Diagram
<img width="985" height="722" alt="зображення" src="https://github.com/user-attachments/assets/978749a2-c6b6-4cf5-9609-6a8641141f7e" />

## 3. Entity List and Attribute Descriptions with constraints

### User
| Attribute          | SQL Type               | Constraints                                     |
|--------------------|------------------------|--------------------------------------------------|
| user_id            | INT                    | PK, AUTO_INCREMENT                               |
| first_name         | VARCHAR(100)           | NOT NULL                                         |
| last_name          | VARCHAR(100)           | NOT NULL                                         |
| email              | VARCHAR(255)           | NOT NULL, UNIQUE                                 |
| phone              | VARCHAR(20)            | NOT NULL, UNIQUE                                 |
| passport_data      | VARCHAR(255)           | NOT NULL                                         |
| driver_license     | VARCHAR(255)           | NOT NULL                                         |
| registration_date  | DATETIME               | NOT NULL                                         |
| status             | ENUM('active','blocked') | NOT NULL                                      |

### Vehicle
| Attribute     | SQL Type                 | Constraints                                     |
|---------------|--------------------------|--------------------------------------------------|
| vehicle_id    | INT                      | PK, AUTO_INCREMENT                               |
| brand         | VARCHAR(100)             | NOT NULL                                         |
| model         | VARCHAR(100)             | NOT NULL                                         |
| plate_number  | VARCHAR(20)              | NOT NULL, UNIQUE                                 |
| vin           | VARCHAR(50)              | NOT NULL, UNIQUE                                 |
| type          | ENUM('electric','petrol','hybrid') | NOT NULL                               |
| status        | ENUM('available','booked','in_trip','maintenance') | NOT NULL |
| location      | VARCHAR(255)             | NOT NULL                                         |
| fuel_level    | TINYINT UNSIGNED         | CHECK(fuel_level BETWEEN 0 AND 100)              |

### Booking
| Attribute    | SQL Type     | Constraints                                                   |
|--------------|--------------|----------------------------------------------------------------|
| booking_id   | INT          | PK, AUTO_INCREMENT                                             |
| user_id      | INT          | FK → User(user_id), NOT NULL                                   |
| vehicle_id   | INT          | FK → Vehicle(vehicle_id), NOT NULL                             |
| start_time   | DATETIME     | NOT NULL                                                       |
| end_time     | DATETIME     | NULL                                                           |
| status       | ENUM('active','expired','cancelled') | NOT NULL                           |

### Trip
| Attribute       | SQL Type          | Constraints                                        |
|-----------------|-------------------|-----------------------------------------------------|
| trip_id         | INT               | PK, AUTO_INCREMENT                                  |
| user_id         | INT               | FK → User(user_id), NOT NULL                         |
| vehicle_id      | INT               | FK → Vehicle(vehicle_id), NOT NULL                   |
| start_time      | DATETIME          | NOT NULL                                            |
| end_time        | DATETIME          | NULL                                                |
| start_location  | VARCHAR(255)      | NOT NULL                                            |
| end_location    | VARCHAR(255)      | NULL                                                |
| distance        | DECIMAL(10,2)     | NOT NULL DEFAULT 0, CHECK(distance >= 0)            |
| cost            | DECIMAL(10,2)     | NOT NULL DEFAULT 0, CHECK(cost >= 0)                |

### Payment
| Attribute    | SQL Type          | Constraints                                                    |
|--------------|-------------------|-----------------------------------------------------------------|
| payment_id   | INT               | PK, AUTO_INCREMENT                                              |
| trip_id      | INT               | FK → Trip(trip_id), NULL                                        |
| booking_id   | INT               | FK → Booking(booking_id), NULL                                  |
| user_id      | INT               | FK → User(user_id), NOT NULL                                     |
| amount       | DECIMAL(10,2)     | NOT NULL, CHECK(amount >= 0)                                     |
| method       | ENUM('card','cash'bonus) | NOT NULL                                         |
| status       | ENUM('pending','paid','failed') | NOT NULL                                     |

### Tariff
| Attribute                | SQL Type          | Constraints                                      |
|--------------------------|-------------------|---------------------------------------------------|
| tariff_id                | INT               | PK, AUTO_INCREMENT                                |
| name                     | VARCHAR(100)      | NOT NULL, UNIQUE                                  |
| price_per_minute         | DECIMAL(10,2)     | NOT NULL, CHECK(price_per_minute >= 0)            |
| included_mileage         | INT               | NOT NULL, CHECK(included_mileage >= 0)            |
| booking_price            | DECIMAL(10,2)     | NOT NULL, CHECK(booking_price >= 0)               |
| booking_duration_minutes | INT               | NOT NULL, CHECK(booking_duration_minutes > 0)     |
| deposit                  | DECIMAL(10,2)     | NOT NULL, CHECK(deposit >= 0)                     |
| insurance                | DECIMAL(10,2)     | NOT NULL, CHECK(insurance >= 0)                   |

### Maintenance
| Attribute      | SQL Type          | Constraints                                        |
|----------------|-------------------|-----------------------------------------------------|
| maintenance_id | INT               | PK, AUTO_INCREMENT                                  |
| vehicle_id     | INT               | FK → Vehicle(vehicle_id), NOT NULL                   |
| type           | VARCHAR(100)      | NOT NULL                                            |
| date           | DATETIME          | NOT NULL                                            |
| mileage        | DECIMAL(10,2)     | NOT NULL, CHECK(mileage >= 0)                       |
| comment        | VARCHAR(255)      | NULL                                                |
| status         | ENUM('planned','done') | NOT NULL                                      |

### Penalty
| Attribute   | SQL Type          | Constraints                                       |
|-------------|-------------------|----------------------------------------------------|
| penalty_id  | INT               | PK, AUTO_INCREMENT                                 |
| user_id     | INT               | FK → User(user_id), NOT NULL                        |
| trip_id     | INT               | FK → Trip(trip_id), NOT NULL                        |
| type        | VARCHAR(150)      | NOT NULL                                           |
| amount      | DECIMAL(10,2)     | NOT NULL, CHECK(amount >= 0)                       |
| date        | DATETIME          | NOT NULL                                           |

## 4 Relationships (one-line format)

- User — Booking: One user can have many bookings; each booking belongs to one user.
- User — Trip: One user can have many trips; each trip belongs to one user.
- User — Payment: One user can make many payments; each payment belongs to one user.
- User — Penalty: One user can receive many penalties; each penalty belongs to one user.
- Vehicle — Booking: One vehicle can have many bookings; each booking uses one vehicle.
- Vehicle — Trip: One vehicle can be used in many trips; each trip uses one vehicle.
- Vehicle — Maintenance: One vehicle has many maintenance records; each record belongs to one vehicle.
- Vehicle — Tariff: Many vehicles can share one tariff; each vehicle has exactly one tariff.
- Booking — Payment: A booking may have multiple payments; each payment may reference a booking.
- Trip — Payment: A trip may have multiple payments; each payment may reference a trip.
- Trip — Penalty: A trip may generate multiple penalties; each penalty is tied to one trip.
