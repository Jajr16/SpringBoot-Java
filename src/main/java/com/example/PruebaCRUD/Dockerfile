# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Instalar ffmpeg
RUN apt-get update && apt-get install -y ffmpeg

# Expone el puerto en el que la app va a correr
EXPOSE 8080

# Mantén el contenedor corriendo sin hacer nada específico
CMD ["tail", "-f", "/dev/null"]
