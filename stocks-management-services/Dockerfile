FROM openjdk:8-jdk-alpine

MAINTAINER Pritam Dsouza (dsouza.pritam@gmail.com)

VOLUME /tmp

COPY ./target/stocks-management-services-0.0.1-SNAPSHOT.jar ./stocks-management-services-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/stocks-management-services-0.0.1-SNAPSHOT.jar"]