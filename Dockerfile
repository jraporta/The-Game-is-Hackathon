FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/bankingapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3000
HEALTHCHECK CMD curl --fail http://localhost:3000 || exit 1
CMD ["java", "-jar", "app.jar"]
