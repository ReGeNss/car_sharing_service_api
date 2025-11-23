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

---
