package com.example.routes.vehicle

import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.put
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import com.example.repositories.vehicle.VehicleRepository
import com.example.dto.vehicle.VehicleCreateDto
import com.example.dto.vehicle.VehicleUpdateDto
import com.example.dto.vehicle.VehicleDto

fun Route.vehicleRoutes() {
    route("/vehicles") {
        post({
            summary = "Create vehicle"
            description = "Create a new vehicle with location and tariff references"
            request {
                body<VehicleCreateDto>()
            }
            response {
                HttpStatusCode.Created to {
                    description = "Created vehicle id"
                    body<Map<String, Int>>()
                }
                HttpStatusCode.BadRequest to {
                    description = "Validation or creation failure"
                }
            }
        }, {
            try {
                val req = call.receive<VehicleCreateDto>()
                val id = VehicleRepository.create(req)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
            }
        })

        get({
            summary = "List vehicles"
            description = "Return all vehicles"
            response {
                HttpStatusCode.OK to {
                    description = "List of vehicles"
                    body<List<VehicleDto>>()
                }
            }
        }, {
            val list = VehicleRepository.listAll()
            call.respond(list)
        })

        get({
            summary = "Get vehicle"
            description = "Get vehicle by id"
            response {
                HttpStatusCode.OK to {
                    description = "Vehicle details"
                    body<VehicleDto>()
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid id supplied"
                }
            }
        }, {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@get
            }
            val vehicle = VehicleRepository.getById(id)
            if (vehicle == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(vehicle)
            }
        })

        put({
            summary = "Update vehicle"
            description = "Update vehicle fields (partial updates allowed)"
            request {
                body<VehicleUpdateDto>()
            }
            response {
                HttpStatusCode.NoContent to {
                    description = "Successfully updated"
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid id or request"
                }
            }
        }, {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@put
            }
            val req = call.receive<VehicleUpdateDto>()
            val updated = VehicleRepository.update(id, req)
            if (updated) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        })

        delete({
            summary = "Delete vehicle"
            description = "Delete vehicle by id"
            response {
                HttpStatusCode.NoContent to {
                    description = "Deleted successfully"
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid id"
                }
            }
        }, {
            val idParam = call.parameters["id"]
            val id = idParam?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@delete
            }
            val deleted = VehicleRepository.delete(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        })
    }
}