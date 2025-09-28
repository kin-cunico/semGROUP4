# setting up provisional dockerfile
# TODO: change copied folder to actual folder // fixing
# TODO: change entrypoint to actual entrypoint // fixing
FROM openjdk:latest
COPY ./target/devops-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "devops-0.1.0.1-jar-with-dependencies.jar"]
