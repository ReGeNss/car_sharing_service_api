plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "com.example"
version = "0.0.1"

application {
    // !!! Переконайся, що тут вказано правильний шлях до твого Main файлу !!!
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // --- Ktor Core ---
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

    // --- Logging ---
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // --- Database (Exposed) ---
    implementation("org.jetbrains.exposed:exposed-core:0.52.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.52.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.52.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.52.0")

    // --- Database Connection Pool (ТЕ ЩО ВИПРАВЛЯЄ ТВОЮ ПОМИЛКУ) ---
    implementation("com.zaxxer:HikariCP:5.1.0") 

    // --- Database Drivers ---
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("com.h2database:h2:2.2.224")

    // --- Tests ---
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks {
    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        mergeServiceFiles() 
    }
}