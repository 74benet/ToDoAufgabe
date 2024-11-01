# Use an official Gradle image to build the Spring Boot app
# Use an official OpenJDK runtime as a base image for running the application
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built application from the previous stage
COPY *.jar app.jar

# Expose the port Spring Boot is running on
EXPOSE 8080

# Set the entry point for the container to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]