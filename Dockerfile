FROM openjdk:17-jdk-slim
COPY ./target/devops-project-0.1.0.5-jar-with-dependencies.jar /tmp
WORKDIR /tmp
# exposing port for debugging
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar", "devops-project-0.1.0.5-jar-with-dependencies.jar"]

