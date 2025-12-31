# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy Maven configuration file
COPY pom.xml .

# Download dependencies (cached layer if pom.xml doesn't change)
# This speeds up subsequent builds by caching dependencies
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests -B

# Runtime stage - Using Eclipse Temurin (official replacement for deprecated OpenJDK images)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/studentrepository-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will provide PORT env variable)
EXPOSE 8080

# Run the application
# Render automatically sets PORT environment variable
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
