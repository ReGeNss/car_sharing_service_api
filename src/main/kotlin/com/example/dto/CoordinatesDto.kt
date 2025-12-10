package com.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import com.example.utils.BigDecimalSerializer

@Serializable
data class CoordinatesDto(
    val id: Int,
    @Serializable(with = BigDecimalSerializer::class) val latitude: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class) val longitude: BigDecimal
)