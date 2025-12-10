package com.example

import com.example.config.DatabaseFactory
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    try {
        DatabaseFactory.init()
    } catch (e: Exception) {
        e.printStackTrace()
        return
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}