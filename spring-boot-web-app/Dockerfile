FROM java:8-jdk-alpine
COPY ./target/spring-boot-web-app-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-web-app-1.0-SNAPSHOT.jar"]

## select parent image
#FROM maven:3.6.3-jdk-8
#
## copy the source tree and the pom.xml to our new container
#COPY ./ /usr/app
#
#WORKDIR /usr/app
#
## package our application code
#RUN mvn clean package
#
## set the startup command to execute the jar
#ENTRYPOINT ["java", "-jar", "target/spring-boot-web-app-1.0-SNAPSHOT.jar"]
