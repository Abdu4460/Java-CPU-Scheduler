# Dockerfile for Spring Boot Back-End
FROM eclipse-temurin:23

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY target/*.jar cpuScheduler-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "cpuScheduler-0.0.1-SNAPSHOT.jar"]
