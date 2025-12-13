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
            description = """
                Create a new vehicle in the car sharing system.
                
                The vehicle will be created with:
                - Reference to an existing vehicle model (modelId)
                - Unique plate number (8 characters, format: AA1234BB)
                - Unique VIN (17 characters)
                - Location coordinates (latitude: 50.40-50.50, longitude: 30.40-30.60)
                - Fuel level (0-100)
                - Reference to an existing tariff (tariffId)
                
                The vehicle status will be automatically set to 'available'.
            """.trimIndent()
            tags = listOf("Vehicles")
            request {
                body<VehicleCreateDto> {
                    description = """
                        Vehicle creation data.
                        
                        Example JSON:
                        {
                          "modelId": 1,
                          "plateNumber": "AA1234BB",
                          "vin": "1HGBH41JXMN109186",
                          "locationLongitude": 30.5234,
                          "locationLatitude": 50.4501,
                          "fuelLevel": 85,
                          "tariffId": 1
                        }
                    """.trimIndent()
                }
            }
            response {
                HttpStatusCode.Created to {
                    description = """
                        Vehicle successfully created. Returns the ID of the newly created vehicle.
                        
                        Example response:
                        {
                          "id": 1
                        }
                    """.trimIndent()
                    body<Map<String, Int>>()
                }
                HttpStatusCode.BadRequest to {
                    description = """
                        Validation or creation failure. Possible reasons:
                        - Invalid modelId (model does not exist)
                        - Duplicate plateNumber or VIN
                        - Invalid coordinates (outside allowed range)
                        - Invalid tariffId (tariff does not exist)
                        - Invalid fuelLevel (must be 0-100)
                    """.trimIndent()
                    body<Map<String, String>> {
                        description = """
                            Example error response:
                            {
                              "error": "Plate number already exists"
                            }
                        """.trimIndent()
                    }
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
            summary = "List all vehicles"
            description = """
                Retrieve a list of all vehicles in the system.
                
                Returns complete vehicle information including:
                - Vehicle details (ID, plate number, VIN, status, fuel level)
                - Vehicle model information (brand, model name, type)
                - Current location coordinates
                - Associated tariff (if assigned)
            """.trimIndent()
            tags = listOf("Vehicles")
            response {
                HttpStatusCode.OK to {
                    description = """
                        List of all vehicles.
                        
                        Example response:
                        [
                          {
                            "id": 1,
                            "model": {
                              "id": 1,
                              "brand": "Tesla",
                              "modelName": "Model 3",
                              "type": "electric"
                            },
                            "plateNumber": "AA1234BB",
                            "vin": "1HGBH41JXMN109186",
                            "status": "available",
                            "location": {
                              "id": 1,
                              "latitude": 50.4501,
                              "longitude": 30.5234
                            },
                            "fuelLevel": 85,
                            "tariff": {
                              "id": 1,
                              "name": "Standard",
                              "pricePerMinute": "0.50",
                              "includedMileage": 10,
                              "bookingPrice": "5.00",
                              "bookingDurationMinutes": 15,
                              "deposit": "100.00",
                              "insurance": "10.00"
                            }
                          }
                        ]
                    """.trimIndent()
                    body<List<VehicleDto>>()
                }
            }
        }, {
            val list = VehicleRepository.listAll()
            call.respond(list)
        })

        get({
            summary = "Get vehicle by ID"
            description = """
                Retrieve detailed information about a specific vehicle by its ID.
                
                Returns complete vehicle information including model, location, and tariff details.
            """.trimIndent()
            tags = listOf("Vehicles")
            request {
                pathParameter<Int>("id") {
                    description = "Vehicle ID (e.g., 1)"
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = """
                        Vehicle details.
                        
                        Example response:
                        {
                          "id": 1,
                          "model": {
                            "id": 1,
                            "brand": "Tesla",
                            "modelName": "Model 3",
                            "type": "electric"
                          },
                          "plateNumber": "AA1234BB",
                          "vin": "1HGBH41JXMN109186",
                          "status": "available",
                          "location": {
                            "id": 1,
                            "latitude": 50.4501,
                            "longitude": 30.5234
                          },
                          "fuelLevel": 85,
                          "tariff": {
                            "id": 1,
                            "name": "Standard",
                            "pricePerMinute": "0.50",
                            "includedMileage": 10,
                            "bookingPrice": "5.00",
                            "bookingDurationMinutes": 15,
                            "deposit": "100.00",
                            "insurance": "10.00"
                          }
                        }
                    """.trimIndent()
                    body<VehicleDto>()
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle with the specified ID was not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid ID format. ID must be a positive integer."
                    body<String> {
                        description = "Example: \"Invalid id\""
                    }
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
            description = """
                Update vehicle information. Supports partial updates - only provided fields will be updated.
                
                All fields are optional. You can update:
                - Vehicle model (modelId)
                - Plate number (must be unique, 8 characters)
                - VIN (must be unique, 17 characters)
                - Status (available, booked, in_trip, maintenance)
                - Location (locationId - reference to existing coordinates)
                - Fuel level (0-100)
                - Tariff (tariffId - reference to existing tariff)
                
                Note: If updating location, provide locationId of existing coordinates.
            """.trimIndent()
            tags = listOf("Vehicles")
            request {
                pathParameter<Int>("id") {
                    description = "Vehicle ID to update (e.g., 1)"
                }
                body<VehicleUpdateDto> {
                    description = """
                        Vehicle update data (all fields optional).
                        
                        Example 1 - Partial update:
                        {
                          "status": "maintenance",
                          "fuelLevel": 90,
                          "tariffId": 2
                        }
                        
                        Example 2 - Full update:
                        {
                          "modelId": 2,
                          "plateNumber": "CC5678DD",
                          "vin": "5YJ3E1EA1KF123456",
                          "status": "available",
                          "locationId": 2,
                          "fuelLevel": 100,
                          "tariffId": 1
                        }
                    """.trimIndent()
                }
            }
            response {
                HttpStatusCode.NoContent to {
                    description = "Vehicle successfully updated"
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle with the specified ID was not found"
                }
                HttpStatusCode.BadRequest to {
                    description = """
                        Invalid request. Possible reasons:
                        - Invalid ID format
                        - Invalid modelId, locationId, or tariffId (references do not exist)
                        - Duplicate plateNumber or VIN
                        - Invalid status value
                        - Invalid fuelLevel (must be 0-100)
                    """.trimIndent()
                    body<String> {
                        description = "Example: \"Invalid id\""
                    }
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
            description = """
                Delete a vehicle from the system by its ID.
                
                Note: The vehicle can only be deleted if there are no active bookings or trips associated with it.
                Deleting a vehicle will also delete associated coordinates if they are not referenced by other vehicles.
            """.trimIndent()
            tags = listOf("Vehicles")
            request {
                pathParameter<Int>("id") {
                    description = "Vehicle ID to delete (e.g., 1)"
                }
            }
            response {
                HttpStatusCode.NoContent to {
                    description = "Vehicle successfully deleted"
                }
                HttpStatusCode.NotFound to {
                    description = "Vehicle with the specified ID was not found"
                }
                HttpStatusCode.BadRequest to {
                    description = "Invalid ID format. ID must be a positive integer."
                    body<String> {
                        description = "Example: \"Invalid id\""
                    }
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