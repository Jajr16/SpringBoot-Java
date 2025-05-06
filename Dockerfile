# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:17-jdk-slim

# Configurar entorno
ENV DEBIAN_FRONTEND=noninteractive

# Instalar dependencias básicas primero
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    apt-transport-https \
    ca-certificates \
    software-properties-common \
    unzip

# Configurar repositorio de Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list

# Instalar Chrome y dependencias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    google-chrome-stable \
    xvfb \
    libglib2.0-0 \
    libnss3 \
    libfontconfig1 \
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
    libgbm1 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    fonts-liberation \
    libu2f-udev \
    xdg-utils \
    && rm -rf /var/lib/apt/lists/*

# Configurar variables de entorno
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV DISPLAY=:99

# Crear directorios necesarios y establecer permisos
WORKDIR /app
RUN mkdir -p /app/src/main/resources/static/images/credenciales && \
    chmod -R 777 /app && \
    mkdir -p /root/.cache && \
    chmod -R 777 /root/.cache

# Copiar el JAR
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Script de inicio mejorado
RUN echo '#!/bin/bash\n\
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\n\
sleep 5\n\
echo "Chrome version:"\n\
google-chrome-stable --version\n\
echo "Starting application..."\n\
java -Dwebdriver.chrome.whitelistedIps="" \
     -Dwebdriver.chrome.verboseLogging=true \
     -Xmx512m -Xms256m \
     -jar /app/app.jar' > /start.sh && \
    chmod +x /start.sh

EXPOSE 8080

CMD ["/start.sh"]