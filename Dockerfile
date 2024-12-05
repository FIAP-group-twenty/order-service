FROM gradle:8.0-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-alpine

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
