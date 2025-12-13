package com.example.repositories.user

import com.example.models.*
import com.example.dto.user.*
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime

object UserRepository {
    fun create(req: RegisterUserRequest, passwordHash: String): Int {
        return Users.insertAndGetId {
            it[firstName] = req.firstName
            it[lastName] = req.lastName
            it[email] = req.email
            it[password] = passwordHash
            it[phone] = req.phone
            it[passportData] = req.passportData
            it[driverLicense] = req.driverLicense
            it[status] = UserStatus.active
            it[registrationDate] = LocalDateTime.now()
        }.value
    }
}