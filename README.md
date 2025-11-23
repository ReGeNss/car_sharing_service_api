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
<img width="985" height="722" alt="зображення" src="https://img.plantuml.biz/plantuml/dpng/lLKnhjim3Drz2iy5V85E7-rSu0zjLqEnv8IeH0aIdIA8_zhpzKYbvKIsPBNuKxCeUXGflaUAVWaaF4r6Dur20hgrtuBoxRrf-VUXdNZJWsm_je0KiQwBomW-K8_2ABO5ye3dY6jHXPKHe7F8NImMfvm8uQVryeWwwwcNWaHkaXwkdA26GM4eWdbr1juA0ekBws9U5WAZuW5cJbFOdDwI1D_L1GQjaWhNTLyLukG55WaRArL9LlFDFKxcnE9cbYjW3j3D5KpAHAFETbYPPJcDazAzLjTu_G9kd3vP-uET4wVJkgzoEbQzga1idNxHivHMeQpYVvVygmUNScEZ---9iKTTrCMdRf7SO0A79TgehLZ1mGPAm4RmLTmC_qqStRfF0N__sXCzAb2jrr4FOUo4b2La55si_7TBxGeXF8pZLeguhHRZ-CgTvuVOEyx904wKwm8uw4agsHhGIfpLaLfqtxiy3N9wFECrRDYVaShP0BH50mny7BT86xSlWWyfZ2Lx4ZGRLcLPRR3Z6wqDc0VHeZjCR8DtLkELCzEyfgvPz-zy7CSkEYPUQPYIovREPylJ6P0D8QKq9AoQ5Gy-8AvxTeEsUCvHlYD-UEQvw-pzEQqoR1aH6V1uKZcs2jGqpnbU12pW6BF0TbsIJ6-sw-RvyJWoX_c-LwLfuY0h4iomHu89AmAyyBXPFiT_0000" />

## 3. Entity List and Attribute Descriptions with constraints

### User
| Attribute         | SQL Type      | Constraints |
|------------------|---------------|-------------|
| user_id          | SERIAL        | PK |
| first_name       | VARCHAR(100)  | NOT NULL |
| last_name        | VARCHAR(100)  | NOT NULL |
| email            | VARCHAR(255)  | NOT NULL, UNIQUE, CHECK (regex email) |
| password         | TEXT          | NOT NULL |
| phone            | VARCHAR(20)   | NOT NULL, UNIQUE, CHECK (`^\+380[0-9]{9}$`) |
| passport_data    | VARCHAR(255)  | NOT NULL, UNIQUE, CHECK (`^[A-Z]{2}[0-9]{6}$`) |
| driver_license   | VARCHAR(255)  | NOT NULL, UNIQUE, CHECK (`^[0-9]{8}$`) |
| registration_date| TIMESTAMP     | NOT NULL |
| status           | user_status   | ENUM('active','blocked') |

### Vehicle
| Attribute     | SQL Type        | Constraints |
|---------------|------------------|-------------|
| vehicle_id    | SERIAL           | PK |
| brand         | VARCHAR(100)     | NOT NULL |
| model         | VARCHAR(100)     | NOT NULL |
| plate_number  | VARCHAR(8)       | NOT NULL, UNIQUE, CHECK (`^[A-Z]{2}[0-9]{4}[A-Z]{2}$`) |
| vin           | VARCHAR(17)      | NOT NULL, UNIQUE, CHECK (`^[A-HJ-NPR-Z0-9]{17}$`) |
| type          | vehicle_type     | ENUM('electric','petrol','hybrid') |
| status        | vehicle_status   | ENUM('available','booked','in_trip','maintenance') |
| location      | INT              | FK → coordinates(coordinates_id) |
| fuel_level    | INT              | CHECK 0–100 |
| tariff_id     | INT              | FK → tariff(tariff_id) |

### Booking
| Attribute   | SQL Type       | Constraints |
|-------------|----------------|-------------|
| booking_id  | SERIAL         | PK |
| user_id     | INT            | FK → users(user_id) |
| vehicle_id  | INT            | FK → vehicle(vehicle_id) |
| start_time  | TIMESTAMP      | NOT NULL |
| end_time    | TIMESTAMP      | NOT NULL |
| status      | booking_status | ENUM('active','expired','cancelled') |


### Trip
| Attribute         | SQL Type      | Constraints |
|-------------------|---------------|-------------|
| trip_id           | SERIAL        | PK |
| user_id           | INT           | FK → users(user_id) |
| vehicle_id        | INT           | FK → vehicle(vehicle_id) |
| start_time        | TIMESTAMP     | NOT NULL |
| end_time          | TIMESTAMP     | NULL |
| start_location    | INT           | FK → coordinates(coordinates_id) |
| end_location      | INT           | FK → coordinates(coordinates_id), NULL |
| distance          | NUMERIC(10,2) | DEFAULT 0, CHECK >= 0 |
| cost              | NUMERIC(10,2) | DEFAULT 0, CHECK >= 0 |


### Payment
| Attribute   | SQL Type        | Constraints |
|-------------|-----------------|-------------|
| payment_id  | SERIAL          | PK |
| trip_id     | INT             | FK → trip(trip_id), NULL |
| booking_id  | INT             | FK → booking(booking_id) |
| user_id     | INT             | FK → users(user_id) |
| amount      | NUMERIC(10,2)   | CHECK >= 0 |
| method      | payment_method  | ENUM('card','cash') |
| status      | payment_status  | ENUM('pending','paid','failed') |


### Tariff
| Attribute                | SQL Type        | Constraints |
|--------------------------|-----------------|-------------|
| tariff_id                | SERIAL          | PK |
| name                     | VARCHAR(100)    | NOT NULL, UNIQUE |
| price_per_minute         | NUMERIC(10,2)   | CHECK >= 0 |
| included_mileage         | INT             | CHECK >= 0 |
| booking_price            | NUMERIC(10,2)   | CHECK >= 0 |
| booking_duration_minutes | INT             | CHECK > 0 |
| deposit                  | NUMERIC(10,2)   | CHECK >= 0 |
| insurance                | NUMERIC(10,2)   | CHECK >= 0 |

### Maintenance
| Attribute      | SQL Type          | Constraints |
|----------------|-------------------|-------------|
| maintenance_id | SERIAL            | PK |
| vehicle_id     | INT               | FK → vehicle(vehicle_id) |
| type           | VARCHAR(100)      | NOT NULL |
| date           | TIMESTAMP         | NOT NULL |
| mileage        | NUMERIC(10,2)     | CHECK >= 0 |
| comment        | VARCHAR(255)      | NULL |
| status         | maintenance_status | ENUM('planned','done') |

### Penalty
| Attribute   | SQL Type      | Constraints |
|-------------|---------------|-------------|
| penalty_id  | SERIAL        | PK |
| user_id     | INT           | FK → users(user_id) |
| trip_id     | INT           | FK → trip(trip_id) |
| type        | VARCHAR(150)  | NOT NULL |
| amount      | NUMERIC(10,2) | CHECK >= 0 |
| date        | TIMESTAMP     | NOT NULL |

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
