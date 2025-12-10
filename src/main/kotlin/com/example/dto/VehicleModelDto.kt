package com.example.dto

import kotlinx.serialization.Serializable
import com.example.models.VehicleType

@Serializable
data class VehicleModelDto(
    val id: Int,
    val brand: String,
    val modelName: String,
    val type: VehicleType
)