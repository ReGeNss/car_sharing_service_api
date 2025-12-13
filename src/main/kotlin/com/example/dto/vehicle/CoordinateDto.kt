package com.example.dto.vehicle

import kotlinx.serialization.Serializable

@Serializable
data class CoordinateDto(
    val id: Int,
    val latitude: Double,
    val longitude: Double
)

