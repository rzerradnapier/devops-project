FROM openjdk:17-jdk-slim
COPY ./target/devops.jar /tmp
WORKDIR /tmp
# exposing port for debugging
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar", "devops.jar", "db:3306", "30000"]

