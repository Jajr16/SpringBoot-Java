FROM eclipse-temurin:17-jdk-jammy

# Instalar FFmpeg
RUN apt-get update && apt-get install -y ffmpeg

# Crear directorio para el volumen
RUN mkdir -p /data/frames

WORKDIR /app
COPY . .
CMD ["./mvnw", "spring-boot:run"]