# semgroup4-db/Dockerfile
FROM mysql:8.0

# Set up environment variables
ENV MYSQL_ROOT_PASSWORD=semgroup4
ENV MYSQL_DATABASE=world
ENV MYSQL_ROOT_HOST=%

# Copy dataset into MySQL’s initialization folder
# Any .sql file in this directory runs automatically on container startup
COPY world.sql /docker-entrypoint-initdb.d/world.sql