# Start with a base image containing Java runtime
FROM openjdk:21

# Add Maintainer Info
LABEL maintainer="scottdickerson@example.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/rbac-api-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} rbac-api.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/rbac-api.jar"]