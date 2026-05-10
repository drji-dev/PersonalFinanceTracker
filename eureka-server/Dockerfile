FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]