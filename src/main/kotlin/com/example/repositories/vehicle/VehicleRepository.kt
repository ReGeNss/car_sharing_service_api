package com.example.repositories.vehicle

import com.example.models.*
import com.example.dto.vehicle.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.selectAll

object VehicleRepository {
    fun create(req: VehicleCreateDto): Int = transaction {
        val defaultStatus = VehicleStatus.available
        Vehicles.insertAndGetId { row ->
            row[model] = EntityID(req.modelId, VehicleModels)
            row[plateNumber] = req.plateNumber
            row[vin] = req.vin
            row[status] = defaultStatus
            row[location] = EntityID(req.locationId, Coordinates)
            row[fuelLevel] = req.fuelLevel
            row[tariff] = EntityID(req.tariffId, Tariffs)
        }.value
    }

    fun getById(id: Int): VehicleDto? = transaction {
        val joined = Vehicles
            .innerJoin(VehicleModels, { Vehicles.model }, { VehicleModels.id })
            .innerJoin(Coordinates, { Vehicles.location }, { Coordinates.id })
            .leftJoin(Tariffs, { Vehicles.tariff }, { Tariffs.id })
        joined.selectAll().where { Vehicles.id eq id }
            .limit(1)
            .map { rowToVehicleDto(it) }
            .firstOrNull()
    }

    fun listAll(): List<VehicleDto> = transaction {
        val joined = Vehicles
            .innerJoin(VehicleModels, { Vehicles.model }, { VehicleModels.id })
            .innerJoin(Coordinates, { Vehicles.location }, { Coordinates.id })
            .leftJoin(Tariffs, { Vehicles.tariff }, { Tariffs.id })
        joined.selectAll().map { rowToVehicleDto(it) }
    }

    fun update(id: Int, req: VehicleUpdateDto): Boolean = transaction {
        val rows = Vehicles.update({ Vehicles.id eq id }) { row ->
            req.modelId?.let { row[model] = EntityID(it, VehicleModels) }
            req.plateNumber?.let { row[plateNumber] = it }
            req.vin?.let { row[vin] = it }
            req.status?.let { row[status] = enumValueOf<VehicleStatus>(it) }
            req.locationId?.let { row[location] = EntityID(it, Coordinates) }
            req.fuelLevel?.let { row[fuelLevel] = it }
            if (req.tariffId != null) {
                row[tariff] = EntityID(req.tariffId, Tariffs)
            }
        }
        rows > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Vehicles.deleteWhere { Vehicles.id eq id } > 0
    }

    private fun rowToVehicleDto(row: ResultRow): VehicleDto {
        val modelDto = VehicleModelDto(
            id = row[VehicleModels.id].value,
            brand = row[VehicleModels.brand],
            modelName = row[VehicleModels.modelName],
            type = row[VehicleModels.type].name
        )

        val coordDto = CoordinateDto(
            id = row[Coordinates.id].value,
            latitude = (row[Coordinates.latitude] as java.math.BigDecimal).toDouble(),
            longitude = (row[Coordinates.longitude] as java.math.BigDecimal).toDouble()
        )

        val tariffDto = row[Tariffs.id].let {
            TariffDto(
                id = it.value,
                name = row[Tariffs.name],
                pricePerMinute = row[Tariffs.pricePerMinute],
                includedMileage = row[Tariffs.includedMileage],
                bookingPrice = row[Tariffs.bookingPrice],
                bookingDurationMinutes = row[Tariffs.bookingDurationMinutes],
                deposit = row[Tariffs.deposit],
                insurance = row[Tariffs.insurance]
            )
        }

        return VehicleDto(
            id = row[Vehicles.id].value,
            model = modelDto,
            plateNumber = row[Vehicles.plateNumber],
            vin = row[Vehicles.vin],
            status = row[Vehicles.status].name,
            location = coordDto,
            fuelLevel = row[Vehicles.fuelLevel],
            tariff = tariffDto
        )
    }
}