package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class AdminDto(
    val id: Int,
    val name: String,
    val login: String,
    val email: String
)