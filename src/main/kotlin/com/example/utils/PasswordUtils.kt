package com.example.utils

import org.mindrot.jbcrypt.BCrypt

object PasswordUtils {
    fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun check(candidate: String, hashed: String): Boolean {
        return BCrypt.checkpw(candidate, hashed)
    }
}