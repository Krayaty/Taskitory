#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
ARG DATABASE_ADDR_ALIAS
ARG DATABASE_PORT
ARG DATABASE_NAME
ARG DATABASE_CLIENT_USER
ARG DATABASE_CLIENT_PW
COPY src /Taskitory/src
COPY pom.xml /Taskitory
RUN mvn -f /Taskitory/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /Taskitory/target/Taskitory-0.0.1-SNAPSHOT.jar /usr/local/lib/Taskitory.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/Taskitory.jar"]
