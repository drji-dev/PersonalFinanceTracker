FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar transaction-service.jar
COPY .env .env
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]