FROM openjdk:17-jdk-slim
VOLUME /data/EntrenamientoIMG

ARG JAR_FILE=target/PruebaCRUD-*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]