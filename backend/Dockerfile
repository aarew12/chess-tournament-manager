FROM openjdk:21-jdk-slim

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew \
&& ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/chess-tournament-backend-0.0.1-SNAPSHOT.jar"]