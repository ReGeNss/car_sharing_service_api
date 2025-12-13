package com.example.routes.user

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.application.*
import io.ktor.http.*
import com.example.dto.user.*
import com.example.services.user.UserService
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get

fun Route.userRoutes() {
    route("/users") {
        post({
            summary = "User registration"
            description = "Accept JSON, validate and record to DB"
            request {
                body<RegisterUserRequest>()
            }
            response {
                HttpStatusCode.Created to {
                    description = "Successfully registered user"
                    body<Map<String, Int>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Validation failure"
                }
            }
        }, {
            try {
                val req = call.receive<RegisterUserRequest>()
                val id = UserService.register(req)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
            }
        })
    }
}