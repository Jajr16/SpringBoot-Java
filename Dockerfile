# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java, FFmpeg y Chrome
FROM openjdk:17-jdk-slim

# Instala FFmpeg y Chrome + dependencias necesarias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ffmpeg \
    libx264-dev \
    chromium \
    chromium-driver \
    libnss3 \
    libgconf-2-4 \
    libglib2.0-0 \
    libfontconfig1 \
    libfreetype6 \
    libx11-6 \
    libx11-xcb1 \
    libxcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrandr2 \
    libxrender1 \
    libxss1 \
    libxtst6 \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Configura variable de entorno para Chrome
ENV CHROME_BIN=/usr/bin/chromium
ENV CHROME_PATH=/usr/lib/chromium/

# Crea directorio para las imágenes
RUN mkdir -p /app/images/
RUN chmod 777 /app/images/

# Copia el .jar
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Ejecuta el .jar con puerto dinámico
CMD ["java", "-Xmx256m", "-Xms128m", "-jar", "/app.jar"]