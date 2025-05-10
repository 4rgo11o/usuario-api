FROM maven:3.9-eclipse-temurin-21 AS dependencies
WORKDIR /app


COPY pom.xml .
RUN mvn dependency:go-offline


FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app


COPY --from=dependencies /root/.m2 /root/.m2
COPY . .

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre-alpine AS execution
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]