FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#set environment variables
ENV SPRING_APPLICATION_NAME=studentrepository
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/studentlogin
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=Alby_um01

FROM openjdk:17-jdk-slim
COPY build/target/demo-0.0.1-SNAPSHOT.jar studentrepository.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "studentrepository.jar"]
