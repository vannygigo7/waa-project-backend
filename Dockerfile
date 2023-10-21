# Use the official Gradle image as the base image
FROM gradle:8.2.1-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY ./ /app

# Build the application
RUN gradle bootJar

# Create a new image for running the application
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8080

# Command to run the application
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
