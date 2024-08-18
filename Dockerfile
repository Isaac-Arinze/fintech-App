FROM openjdk:17
ARG JAR_FILE=target/fintech-Bank-App.jar
COPY ${JAR_FILE} fintech-Bank-App.jar
ENTRYPOINT ["java", "-jar", "/fintech-Bank-App.jar"]
EXPOSE 8080

