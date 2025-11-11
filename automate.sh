#!/bin/bash

# Config

PROJECT_NAME="app"
JAR_FILE="target/semGROUP4-0.1.0.3-jar-with-dependencies.jar"

# Exit if fails
set -e

echo "1. Building and packaging project: "
# this will delete and rebuild the project before packaging
docker run -v "$(pwd)":/app \
-v "$HOME/.m2":/root/.m2 \
-w /app \
maven:3.9.4-amazoncorretto-21 \
mvn clean package
echo "Maven build complete. File at: $JAR_FILE"

# check for jar files
if [ ! -f "$JAR_FILE" ]; then
  echo "Error: Jar file not found. Exiting."
  exit 1
fi

echo "2. Shutting down old containers before creating a new one: "
# -v to remove volumes; -r to remove containers and networks
docker compose down -v --remove-orphans || true # if no container exists, the script will still run
echo "Old containers removed."

echo "3. Building new docker containers: "
docker compose build
echo "New docker images built."

echo "4. Starting docker containers with interactivity to use terminal: "
docker compose run -it $PROJECT_NAME

echo "Deployment complete."

docker compose ps -a