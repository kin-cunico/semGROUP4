# setting up provisional dockerfile
FROM openjdk:17
WORKDIR /app

# Copy the built JAR into the image
COPY target/semGROUP4-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]