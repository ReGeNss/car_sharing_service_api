package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class VehicleModelDto(
    val type: String,
    val modelName: String,
    val brand: String,
    val id: Int,
)

