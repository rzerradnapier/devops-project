# Use the latest MySQL image
FROM mysql/mysql-server:latest
# Set the working directory
WORKDIR /tmp
# Copy all the files to the working directory of the container
COPY world_db/*.sql /tmp/
#COPY world_db/*.dump /tmp/ -- No need for this since no use of dump file
# Copy the main SQL file to docker-entrypoint-initdb.d.
# Scripts and SQL files in this folder are executed on container startup.
# This is specific to MySQL.
COPY world_db/world.sql /docker-entrypoint-initdb.d
# Set the root password
ENV MYSQL_ROOT_PASSWORD=ei:UA@_oSnDZ

# allow root from any host
ENV MYSQL_ROOT_HOST=%