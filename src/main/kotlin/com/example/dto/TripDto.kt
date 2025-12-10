package com.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import com.example.utils.BigDecimalSerializer
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class TripDto(
    val id: Int,
    val userId: Int,
    val vehicleId: Int,
    @Serializable(with = LocalDateTimeSerializer::class) val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class) val endTime: LocalDateTime?,
    val startLocationId: Int,
    val endLocationId: Int?,
    @Serializable(with = BigDecimalSerializer::class) val distance: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class) val cost: BigDecimal
)