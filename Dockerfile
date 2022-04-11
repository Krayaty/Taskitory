FROM maven:3.8.4-openjdk-17-slim AS DEPS
WORKDIR /opt/Taskitory

COPY 0-Abstraction/pom.xml 0-Abstraction/pom.xml
COPY 1-Domain/pom.xml 1-Domain/pom.xml
COPY 2-Application/pom.xml 2-Application/pom.xml
COPY 3-Adapter/pom.xml 3-Adapter/pom.xml
COPY 4-Plugins/pom.xml 4-Plugins/pom.xml
COPY pom.xml .

RUN mvn -B -e -C compile dependency:tree


FROM maven:3.8.4-openjdk-17-slim AS BUILDER
COPY --from=deps /root/.m2 /root/.m2
COPY --from=deps /opt/Taskitory/ /opt/Taskitory

WORKDIR /opt/Taskitory
COPY 0-Abstraction/src 0-Abstraction/src
COPY 1-Domain/src 1-Domain/src
COPY 2-Application/src 2-Application/src
COPY 3-Adapter/src 3-Adapter/src
COPY 4-Plugins/src 4-Plugins/src

RUN mvn -B -e -C clean install -DskipTests=true


FROM openjdk:17-jdk-slim AS DEPLOYER
COPY --from=builder /opt/Taskitory/target/Plugins-0.0.1-exe.jar /opt/Taskitory/Taskitory.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/Taskitory/Taskitory.jar" ]
