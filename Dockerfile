#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /opt/Taskitory
COPY Abstraction Abstraction
COPY Domain Domain
COPY Application Application
COPY Adapter Adapter
COPY pom.xml .
RUN mvn -f pom.xml -DskipTests=true clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /opt/Taskitory/target/Abstraction-0.0.1.jar /usr/local/lib/Taskitory.jar
# COPY --from=build /opt/Taskitory/target/Domain-0.0.1.jar /usr/local/lib/Domain.jar
# COPY --from=build /opt/Taskitory/target/Application-0.0.1.jar /usr/local/lib/Application.jar
# COPY --from=build /opt/Taskitory/target/Adapter-0.0.1.jar /usr/local/lib/Adapter.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/Taskitory.jar"]
