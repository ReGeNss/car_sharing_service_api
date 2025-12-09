package com.example.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.CurrentDateTime


object Coordinates : IntIdTable("coordinates", "coordinates_id") {
    val latitude = decimal("latitude", 9, 6).check { it.between(-90.toBigDecimal(), 90.toBigDecimal()) }
    val longitude = decimal("longitude", 9, 6).check { it.between(-180.toBigDecimal(), 180.toBigDecimal()) }
}

object Tariffs : IntIdTable("tariff", "tariff_id") {
    val name = varchar("name", 100).uniqueIndex()
    val pricePerMinute = decimal("price_per_minute", 10, 2)
    val includedMileage = integer("included_mileage")
    val bookingPrice = decimal("booking_price", 10, 2)
    val bookingDurationMinutes = integer("booking_duration_minutes")
    val deposit = decimal("deposit", 10, 2)
    val insurance = decimal("insurance", 10, 2)
}

object Users : IntIdTable("users", "user_id") {
    val firstName = varchar("first_name", 100)
    val lastName = varchar("last_name", 100)
    val email = varchar("email", 255).uniqueIndex()
    val password = text("password")
    val phone = varchar("phone", 20).uniqueIndex()
    val passportData = varchar("passport_data", 255).uniqueIndex()
    val driverLicense = varchar("driver_license", 255).uniqueIndex()
    val registrationDate = datetime("registration_date").defaultExpression(CurrentDateTime)

    val status = enumerationByName("status", 20, UserStatus::class)
}

object VehicleModels : IntIdTable("vehicle_model", "model_id") {
    val brand = varchar("brand", 100)
    val modelName = varchar("model_name", 100)
    val type = enumerationByName("type", 20, VehicleType::class)

    init {
        uniqueIndex(brand, modelName) 
    }
}

object Vehicles : IntIdTable("vehicle", "vehicle_id") {
    val model = reference("model_id", VehicleModels, onDelete = ReferenceOption.RESTRICT)
    val plateNumber = varchar("plate_number", 8).uniqueIndex()
    val vin = varchar("vin", 17).uniqueIndex()
    val status = enumerationByName("status", 20, VehicleStatus::class)
    val location = reference("location", Coordinates, onDelete = ReferenceOption.RESTRICT)
    val fuelLevel = integer("fuel_level")
    val tariff = reference("tariff_id", Tariffs, onDelete = ReferenceOption.SET_NULL)
}

object Bookings : IntIdTable("booking", "booking_id") {
    val user = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val vehicle = reference("vehicle_id", Vehicles, onDelete = ReferenceOption.RESTRICT)

    val startTime = datetime("start_time")
    val endTime = datetime("end_time")
    val status = enumerationByName("status", 20, BookingStatus::class)
}

object Trips : IntIdTable("trip", "trip_id") {
    val user = reference("user_id", Users, onDelete = ReferenceOption.CASCADE)
    val vehicle = reference("vehicle_id", Vehicles, onDelete = ReferenceOption.CASCADE)

    val startTime = datetime("start_time")
    val endTime = datetime("end_time").nullable()

    val startLocation = reference("start_location", Coordinates, onDelete = ReferenceOption.RESTRICT)
    val endLocation = reference("end_location", Coordinates, onDelete = ReferenceOption.RESTRICT).nullable()

    val distance = decimal("distance", 10, 2).default(java.math.BigDecimal.ZERO)
    val cost = decimal("cost", 10, 2).default(java.math.BigDecimal.ZERO)
}

object Payments : IntIdTable("payment", "payment_id") {
    val trip = reference("trip_id", Trips, onDelete = ReferenceOption.CASCADE).nullable()
    val booking = reference("booking_id", Bookings, onDelete = ReferenceOption.CASCADE)
    
    val amount = decimal("amount", 10, 2)
    val method = enumerationByName("method", 20, PaymentMethod::class)
    val status = enumerationByName("status", 20, PaymentStatus::class)
}

object Maintenances : IntIdTable("maintenance", "maintenance_id") {
    val vehicle = reference("vehicle_id", Vehicles, onDelete = ReferenceOption.CASCADE)
    
    val type = varchar("type", 100)
    val date = datetime("date")
    val mileage = decimal("mileage", 10, 2)
    val comment = varchar("comment", 255).nullable()
    val status = enumerationByName("status", 20, MaintenanceStatus::class)
}

object Penalties : IntIdTable("penalty", "penalty_id") {
    val trip = reference("trip_id", Trips, onDelete = ReferenceOption.CASCADE)
    
    val type = varchar("type", 150)
    val amount = decimal("amount", 10, 2)
    val date = datetime("date")
}