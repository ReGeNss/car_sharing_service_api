package com.example.dto

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import com.example.models.MaintenanceStatus
import com.example.utils.BigDecimalSerializer
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class MaintenanceDto(
    val id: Int,
    val vehicleId: Int,
    val type: String,
    @Serializable(with = LocalDateTimeSerializer::class) val date: LocalDateTime,
    @Serializable(with = BigDecimalSerializer::class) val mileage: BigDecimal,
    val comment: String?,
    val status: MaintenanceStatus
)