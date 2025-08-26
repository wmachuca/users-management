# Build stage
FROM gradle:8.9-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY src /app/src
# Pre-fetch dependencies and build the boot jar
RUN gradle clean bootJar --no-daemon

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copy the executable jar from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar
# Expose default Spring Boot port
EXPOSE 8080
# Set active profile if needed
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
