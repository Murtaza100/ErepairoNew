FROM openjdk:11
EXPOSE 9099
ADD target/chat-service.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]