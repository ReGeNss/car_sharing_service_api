package com.example.dto.user

import kotlinx.serialization.Serializable
import com.example.models.PaymentMethod
import com.example.models.UserStatus
import com.example.utils.BigDecimalSerializer
import java.math.BigDecimal

@Serializable
data class RegisterUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val passportData: String,
    val driverLicense: String
)