FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/fintech-Bank-App-0.0.1-SNAPSHOT.jar fintech-Bank-App.jar
#ARG JAR_FILE=target/fintech-Bank-App.jar
#COPY ${JAR_FILE} fintech-Bank-App.jar
ENTRYPOINT ["java", "-jar", "/fintech-Bank-App.jar"]
EXPOSE 8080

