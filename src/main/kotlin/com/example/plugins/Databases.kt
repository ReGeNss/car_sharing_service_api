package com.example.plugins

import io.ktor.server.application.*
import com.example.config.DatabaseFactory

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
