package com.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import com.example.models.PaymentMethod
import com.example.models.PaymentStatus
import com.example.utils.BigDecimalSerializer

@Serializable
data class PaymentDto(
    val id: Int,
    val tripId: Int?,
    val bookingId: Int,
    @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
    val method: PaymentMethod,
    val status: PaymentStatus
)