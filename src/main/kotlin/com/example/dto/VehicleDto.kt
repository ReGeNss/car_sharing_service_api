package com.example.dto

import kotlinx.serialization.Serializable
import com.example.models.VehicleStatus

@Serializable
data class VehicleDto(
    val id: Int,
    val modelId: Int,
    val plateNumber: String,
    val vin: String,
    val status: VehicleStatus,
    val locationId: Int,
    val fuelLevel: Int,
    val tariffId: Int?
)