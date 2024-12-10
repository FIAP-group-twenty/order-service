FROM gradle:8.0-jdk17 AS builder

WORKDIR /app

RUN mkdir -p /app/.gradle
ENV GRADLE_USER_HOME=/app/.gradle

COPY build.gradle settings.gradle ./
COPY src ./src

RUN mkdir -p /home/gradle/.gradle && chmod -R 777 /home/gradle/.gradle

RUN gradle clean build --refresh-dependencies --no-daemon

FROM openjdk:17-jdk-alpine

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
