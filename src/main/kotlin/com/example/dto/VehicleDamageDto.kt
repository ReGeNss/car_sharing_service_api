package com.example.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class VehicleDamageDto(
    val id: Int,
    val vehicleId: Int,
    val photoUrl: String,
    val description: String?,
    @Serializable(with = LocalDateTimeSerializer::class) val reportedAt: LocalDateTime
)