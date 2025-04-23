# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java y FFmpeg
FROM openjdk:17-jdk-slim

# Instala FFmpeg
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ffmpeg \
    libx264-dev \
    && rm -rf /var/lib/apt/lists/*

# Copia el .jar
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Ejecuta el .jar con puerto dinámico
CMD sh -c "java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar /app.jar"
