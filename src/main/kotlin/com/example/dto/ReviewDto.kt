package com.example.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class ReviewDto(
    val id: Int,
    val tripId: Int,
    val rating: Int,
    val comment: String?,
    @Serializable(with = LocalDateTimeSerializer::class) val createdAt: LocalDateTime
)