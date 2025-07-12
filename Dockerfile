# Stage 1: Use official OpenJDK 17 with Maven installed
FROM eclipse-temurin:17-jdk AS build

WORKDIR /build
COPY . /build

# Install Maven manually
RUN apt-get update && \
    apt-get install -y maven && \
    mvn -version

# Compile and package
RUN mvn clean package

# Stage 2: Run the Selenium test inside a Chrome container
FROM selenium/standalone-chrome:latest
USER root
WORKDIR /app

COPY --from=build /build/target/selenium-docker-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/test.jar

CMD ["java", "-jar", "/app/test.jar"]