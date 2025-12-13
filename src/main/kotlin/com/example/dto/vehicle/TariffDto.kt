package com.example.dto.vehicle

import com.example.utils.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TariffDto(
    val id: Int,
    val name: String,
    @Serializable(with = BigDecimalSerializer::class)
    val pricePerMinute: BigDecimal,
    val includedMileage: Int,
    @Serializable(with = BigDecimalSerializer::class)
    val bookingPrice: BigDecimal,
    val bookingDurationMinutes: Int,
    @Serializable(with = BigDecimalSerializer::class)
    val deposit: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val insurance: BigDecimal
)