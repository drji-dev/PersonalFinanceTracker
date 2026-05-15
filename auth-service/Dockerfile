FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]