package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleUpdateDto(
    val modelId: Int? = null,
    val plateNumber: String? = null,
    val vin: String? = null,
    val status: String? = null,
    val locationId: Int? = null,
    val fuelLevel: Int? = null,
    val tariffId: Int? = null
)

