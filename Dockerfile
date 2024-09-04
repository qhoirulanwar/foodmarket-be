# Use a base image with Maven and Java (Temurin JDK 22)
FROM maven:3-eclipse-temurin-22 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project directory to the working directory inside the container
COPY . .

# Run Maven to clean and package the project into a JAR file
RUN mvn clean package

# Use a lightweight base image with the Temurin JDK 22 for the runtime
FROM eclipse-temurin:22-jdk-alpine

# Set the working directory inside the container for the runtime environment
WORKDIR /app

# Copy the generated JAR file from the build stage to the current working directory
COPY --from=build /app/target/*.jar /app/app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
