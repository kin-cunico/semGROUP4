# dockerfile for our main app
# for consistency, we should always point to jdk 17
FROM amazoncorretto:17
WORKDIR /app

# Copy the built JAR into the image
COPY target/semGROUP4-0.1.0.3-jar-with-dependencies.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

#CMD for default ports
CMD ["db:3306", "30000"]