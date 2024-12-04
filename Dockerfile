# -----------------------------------
# CONFIGURATIONS ONLY FOR DEPLOY
# JAR MUST BE BUILT BEFORE DEPLOYING
# -----------------------------------

#FROM openjdk:17-jdk-alpine
#WORKDIR /app-docker
#COPY build/libs/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

# -----------------------------------
# CONFIGURATIONS FOR BUILD AND DEPLOY
# -----------------------------------

FROM gradle:latest AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
