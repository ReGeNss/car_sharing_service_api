package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleCreateDto(
    val modelId: Int,
    val plateNumber: String,
    val vin: String,
    val locationLongitude: Double,
    val locationLatitude: Double,
    val fuelLevel: Int,
    val tariffId: Int,
)

