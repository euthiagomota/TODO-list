FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk

COPY . .

RUN apt-get install -y maven && \
    mvn clean install

FROM openjdk:11-jdk-slim

EXPOSE 8080

COPY --from=build /target/todolist-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
