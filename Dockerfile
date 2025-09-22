# setting up provisional dockerfile
# TODO: change copied folder to actual folder
# TODO: change entrypoint to actual entrypoint
FROM openjdk:latest
COPY /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "/tmp"]
