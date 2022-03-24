FROM maven:3.8.4-openjdk-17-slim AS DEPS
WORKDIR /opt/Taskitory

COPY Domain/pom.xml Domain/pom.xml
COPY Application/pom.xml Application/pom.xml
COPY Adapter/pom.xml Adapter/pom.xml
COPY Plugins/pom.xml Plugins/pom.xml
COPY pom.xml .

RUN mvn -B -e -C compile dependency:tree


FROM maven:3.8.4-openjdk-17-slim AS BUILDER
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/Taskitory/ /opt/Taskitory

WORKDIR /opt/Taskitory
COPY Domain/src Domain/src
COPY Application/src Application/src
COPY Adapter/src Adapter/src
COPY Plugins/src Plugins/src

RUN mvn -B -e -C clean install -DskipTests=true


FROM openjdk:17-jdk-slim AS DEPLOYER
COPY --from=builder /opt/Taskitory/target/Taskitory.0.0.1.jar /opt/Taskitory/Taskitory.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/Taskitory/Taskitory.jar" ]
