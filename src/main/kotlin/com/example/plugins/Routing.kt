package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import com.example.routes.user.userRoutes
import com.example.routes.vehicle.vehicleRoutes
import io.ktor.server.response.respondText

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/api") {
            userRoutes()
            vehicleRoutes()
        }
    }
}
