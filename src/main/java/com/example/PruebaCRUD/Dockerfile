FROM openjdk:17-slim

# Instalar ffmpeg
RUN apt-get update && apt-get install -y ffmpeg && apt-get clean

# Copiar y construir tu app
COPY target/tu-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]