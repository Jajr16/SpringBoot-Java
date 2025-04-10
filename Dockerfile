FROM eclipse-temurin:17-jdk-jammy

# Instalar FFmpeg y preparar el volumen
RUN apt-get update && \
    apt-get install -y ffmpeg && \
    mkdir -p /data/frames && \
    chmod 777 /data/frames && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar archivos y dar permisos
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline

COPY src ./src

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]