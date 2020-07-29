FROM java:8-jdk-alpine
COPY ./target/spring-boot-rest-app-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "spring-boot-rest-app-1.0-SNAPSHOT.jar"]


## back
## устанавливаем самую лёгкую версию JVM
#FROM openjdk:8-jdk-alpine
#
##WORKDIR /app
#
## указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Линукс)
#VOLUME /tmp
#
## внешний порт, по которому наше приложение будет доступно извне
#EXPOSE 8088
#
## указываем, где в нашем приложении лежит джарник
#ARG JAR_FILE=target/spring-boot-rest-app-1.0-SNAPSHOT.jar
#
## добавляем джарник в образ под именем spring-boot-rest-app.jar
#ADD ${JAR_FILE} spring-boot-rest-app.jar
#
## команда запуска джарника
#ENTRYPOINT ["java","-jar","/spring-boot-rest-app.jar"]



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
#ENTRYPOINT ["java", "-jar", "target/spring-boot-rest-app-1.0-SNAPSHOT.jar"]



## the first stage of our build will use a maven 3.6.1 parent image
#FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD
#
## copy the pom and src code to the container
#COPY ./ ./
#
## package our application code
#RUN mvn clean package
#
## the second stage of our build will use open jdk 8 on alpine 3.9
#FROM openjdk:8-jre-alpine3.9
#
## copy only the artifacts we need from the first stage and discard the rest
#COPY --from=MAVEN_BUILD spring-boot-rest-app/target/spring-boot-rest-app-1.0-SNAPSHOT.jar /spring-boot-rest-app.jar
#
## set the startup command to execute the jar
#CMD ["java", "-jar", "/spring-boot-rest-app.jar"]
