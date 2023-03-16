FROM openjdk:8-jdk-alpine
LABEL maintainer="ahmedbaz1024"
WORKDIR /usr/local/bin/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} currency-conversion-service.jar
EXPOSE 8100
CMD ["java","-jar","currency-conversion-service.jar"]
