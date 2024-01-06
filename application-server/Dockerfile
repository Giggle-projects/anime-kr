FROM openjdk:21-jdk
RUN mkdir -p infra-database
RUN mkdir -p application-server
COPY infra-database infra-database
COPY application-server/build/libs/*.jar application-server/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application-server/app.jar"]
