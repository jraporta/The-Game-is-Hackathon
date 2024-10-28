FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/myapp.jar app.jar
EXPOSE 3000
CMD ["java", "-jar", "app.jar"]
