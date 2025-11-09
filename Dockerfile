# semgroup4-db/Dockerfile
FROM mysql:8.0

# Configure MySQL environment
ENV MYSQL_ROOT_PASSWORD=semgroup4
ENV MYSQL_DATABASE=world
ENV MYSQL_ROOT_HOST=%

# Copy the world dataset into MySQL's initialization folder
# Any .sql file here runs automatically on first startup
COPY world.sql /docker-entrypoint-initdb.d/world.sql

# Default command to start MySQL
CMD ["mysqld"]