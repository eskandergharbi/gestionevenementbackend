# Use the official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/service_notification-0.0.1-SNAPSHOT.jar /app/service_notification.jar

# Expose the port the app runs on
EXPOSE 8200

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/service_notification.jar"]
