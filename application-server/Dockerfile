FROM openjdk:21-jdk
RUN mkdir -p infra-database
RUN mkdir -p application-server
RUN sudo ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
COPY infra-database infra-database
COPY application-server/build/libs/anime-kr-latest.jar application-server/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/application-server/app.jar"]
