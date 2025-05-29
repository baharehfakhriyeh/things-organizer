# api-gateway/Dockerfile
# ------------ Stage 1: Build the app ------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /things-organizer

# Copy everything and build only once
COPY . .

# This will build module
RUN --mount=type=cache,target=/root/.m2 mvn clean package -pl api-gateway -am -DskipTests

# ------------ Stage 2: Run the app ------------
FROM eclipse-temurin:17-jdk

WORKDIR /things-organizer

# Copy the main application jar from the builder stage
COPY --from=builder /things-organizer/api-gateway/target/*.jar api-gateway.jar

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
