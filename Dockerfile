# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/accounts-0.0.1-SNAPSHOT.jar /app/accounts.jar

# Expose the port that the Spring Boot app will run on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/accounts.jar"]
