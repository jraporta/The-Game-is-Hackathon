FROM maven:3.9.9 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/bankingapp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3000
HEALTHCHECK CMD curl --fail http://localhost:3000 || exit 1
CMD ["java", "-jar", "app.jar"]
