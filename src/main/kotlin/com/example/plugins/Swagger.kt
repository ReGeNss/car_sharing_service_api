package com.example.plugins

import io.ktor.server.application.*
import io.github.smiley4.ktorswaggerui.SwaggerUI

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = true
        }
        info {
            title = "Car Sharing API"
            version = "1.0.0"
        }
    }
}