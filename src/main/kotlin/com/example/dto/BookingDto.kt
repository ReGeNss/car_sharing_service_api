package com.example.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import com.example.models.BookingStatus
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class BookingDto(
    val id: Int,
    val userId: Int,
    val vehicleId: Int,
    @Serializable(with = LocalDateTimeSerializer::class) val startTime: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class) val endTime: LocalDateTime,
    val status: BookingStatus
)
