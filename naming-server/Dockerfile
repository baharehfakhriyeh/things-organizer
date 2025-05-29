# naming-server/Dockerfile
# ------------ Stage 1: Build the app ------------
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /things-organizer

# Copy everything and build only once
COPY . .

# This will build module
RUN --mount=type=cache,target=/root/.m2 mvn clean package -pl naming-server -am -DskipTests

# ------------ Stage 2: Run the app ------------
FROM eclipse-temurin:17-jdk

WORKDIR /things-organizer

# Copy the main application jar from the builder stage
COPY --from=builder /things-organizer/naming-server/target/*.jar naming-server.jar

ENTRYPOINT ["java", "-jar", "naming-server.jar"]
