# dockerfile for our main app
# TODO: for consistency, we should always point to jdk 17
FROM openjdk:17
WORKDIR /app

# Copy the built JAR into the image
COPY target/semGROUP4-0.1.0.2-jar-with-dependencies.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]