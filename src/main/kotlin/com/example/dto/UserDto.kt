package com.example.dto

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import com.example.models.UserStatus
import com.example.utils.LocalDateTimeSerializer

@Serializable
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val passportData: String,
    val driverLicense: String,
    val driverLicensePhotoUrl: String?,
    @Serializable(with = LocalDateTimeSerializer::class) val registrationDate: LocalDateTime,
    val status: UserStatus
)