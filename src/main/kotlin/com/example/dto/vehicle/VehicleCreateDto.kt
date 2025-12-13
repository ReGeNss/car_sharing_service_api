package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleCreateDto(
    val modelId: Int,
    val plateNumber: String,
    val vin: String,
    val locationId: Int,
    val fuelLevel: Int,
    val tariffId: Int,
)

