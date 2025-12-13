package com.example.dto.vehicle

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TariffDto(
    val id: Int,
    val name: String,
    val pricePerMinute: BigDecimal,
    val includedMileage: Int,
    val bookingPrice: BigDecimal,
    val bookingDurationMinutes: Int,
    val deposit: BigDecimal,
    val insurance: BigDecimal
)