# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-24 AS build

WORKDIR /workspace

COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline --batch-mode

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package --batch-mode


FROM eclipse-temurin:24-jre-alpine AS runtime

RUN addgroup --system app \
    && adduser --system --ingroup app app

WORKDIR /app

COPY --from=build --chown=app:app /workspace/target/*.jar app.jar

USER app

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
