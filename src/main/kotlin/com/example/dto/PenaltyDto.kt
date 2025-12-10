package com.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import com.example.utils.BigDecimalSerializer
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class PenaltyDto(
    val id: Int,
    val tripId: Int,
    val type: String,
    @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
    @Serializable(with = LocalDateTimeSerializer::class) val date: LocalDateTime
)