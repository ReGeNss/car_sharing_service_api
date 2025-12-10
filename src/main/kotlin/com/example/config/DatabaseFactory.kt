package com.example.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val host = System.getenv("DB_HOST")
        val port = System.getenv("DB_PORT")
        val databaseName = System.getenv("DB_NAME")
        val user = System.getenv("DB_USER")
        val password = System.getenv("DB_PASSWORD")

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://$host:$port/$databaseName"
            driverClassName = "org.postgresql.Driver"
            username = user
            this.password = password
            validate()
        }
        
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)

        transaction {
            addLogger(StdOutSqlLogger)
        }
    }
}