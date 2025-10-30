
FROM gradle:jdk24-alpine AS build
WORKDIR /app

COPY settings.gradle build.gradle /app/

RUN gradle dependencies --no-daemon || true

COPY src /app/src

RUN gradle clean bootJar --no-daemon

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*-SNAPSHOT.jar /app/app.jar

ENV SERVER_PORT=${SERVER_PORT:-8088}
EXPOSE 8081

ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-local}

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+UseZGC"

ENTRYPOINT ["java", "-jar", "app.jar"]