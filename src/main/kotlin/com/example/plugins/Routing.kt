package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.example.routes.user.userRoutes
import io.ktor.server.response.respondText

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/api") {
            userRoutes()
        }
    }
}
