package com.example.dto.user

import com.example.models.UserStatus
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val passportData: String,
    val driverLicense: String,
    val status: UserStatus = UserStatus.active
)
