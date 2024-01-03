FROM openjdk:21-jdk
RUN mkdir -p data-files
RUN mkdir -p data-images
RUN mkdir -p api-server
COPY data-files data-files
COPY data-images data-images
COPY api-server/build/libs/*.jar api-server/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api-server/app.jar"]