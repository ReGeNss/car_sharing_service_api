# Normalization Report — Car Rental Database

## 1. Introduction

This report analyzes and evaluates the database schema of a car rental service. The objective is to:

* Document functional dependencies (FDs) for every table in the initial design
* Identify redundancies and transitive dependencies
* Determine the highest normal form of the initial design
* Demonstrate the normalization process
* Provide the final SQL DDL reflecting a fully normalized schema

---

## 2. Functional Dependencies of the Initial Schema

### 2.1. coordinates

**Primary key:** `coordinates_id`
**FD:**

```
coordinates_id → latitude, longitude
```

### 2.2. tariff

**Primary key:** `tariff_id`
**FD:**

```
tariff_id → name, price_per_minute, included_mileage,
            booking_price, booking_duration_minutes,
            deposit, insurance
```

### 2.3. users

**Primary key:** `user_id`
**FD:**

```
user_id → first_name, last_name, email, password,
          phone, passport_data, driver_license,
          registration_date, status
```

### 2.4. vehicle

**Primary key:** `vehicle_id`
**FD:**

```
vehicle_id → brand, model, plate_number, vin,
             type, status, location, fuel_level, tariff_id
```

**Transitive dependency:**

```
model → brand, type
```

### 2.5. booking

**Primary key:** `booking_id`
**FD:**

```
booking_id → user_id, vehicle_id, start_time, end_time, status
```

### 2.6. trip

**Primary key:** `trip_id`
**FD:**

```
trip_id → user_id, vehicle_id, start_time, end_time,
          start_location, end_location, distance, cost
```

### 2.7. payment

**Primary key:** `payment_id`
**FD:**

```
payment_id → trip_id, booking_id, user_id, amount, method, status
```

**Redundant:**

```
booking_id → user_id
```

### 2.8. maintenance

**Primary key:** `maintenance_id`
**FD:**

```
maintenance_id → vehicle_id, type, date, mileage, comment, status
```

### 2.9. penalty

**Primary key:** `penalty_id`
**FD:**

```
penalty_id → user_id, trip_id, type, amount, date
```

**Redundant:**

```
trip_id → user_id
```
