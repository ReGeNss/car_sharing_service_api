FROM gradle:8.11-jdk17-alpine AS builder
WORKDIR /app

COPY . .
RUN gradle clean buildFatJar -x test --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
