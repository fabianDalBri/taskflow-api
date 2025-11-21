# Use a lightweight JVM base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Download dependencies first (cached)
RUN ./mvnw dependency:go-offline

# Copy the project source
COPY src ./src

# Build application
RUN ./mvnw -DskipTests package

# Expose port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/taskflow-api-0.0.1-SNAPSHOT.jar"]
