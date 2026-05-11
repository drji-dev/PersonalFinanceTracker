FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar transaction-service.jar
ENTRYPOINT ["java", "-jar", "transaction-service.jar"]