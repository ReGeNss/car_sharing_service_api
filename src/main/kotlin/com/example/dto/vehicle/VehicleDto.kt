package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDto(
    val id: Int,
    val model: VehicleModelDto,
    val plateNumber: String,
    val vin: String,
    val status: String, // maps to VehicleStatus enum name
    val location: CoordinateDto,
    val fuelLevel: Int,
    val tariff: TariffDto? // nullable if no tariff assigned
)
