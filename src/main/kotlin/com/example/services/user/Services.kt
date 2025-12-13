package com.example.services.user

import com.example.dto.user.*
import com.example.repositories.user.*
import com.example.utils.PasswordUtils
import org.jetbrains.exposed.sql.transactions.transaction

object UserService {
    fun register(request: RegisterUserRequest): Int = transaction {
        if (!request.email.contains("@")) throw IllegalArgumentException("Invalid email")
        val passwordHash = PasswordUtils.hash(request.password)
        UserRepository.create(request, passwordHash)
    }
}