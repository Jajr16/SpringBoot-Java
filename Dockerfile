# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:17-jdk-slim

# Configurar entorno
ENV DEBIAN_FRONTEND=noninteractive

# Configurar repositorio de Chrome
RUN apt-get update && apt-get install -y wget gnupg2
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list

# Instalar dependencias y Chrome
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    google-chrome-stable \
    ffmpeg \
    chromium \
    chromium-driver \
    xvfb \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
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
    libasound2 \
    libatk1.0-0 \
    libatspi2.0-0 \
    libcups2 \
    libdbus-1-3 \
    libdrm2 \
    libgtk-3-0 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    # Nuevas dependencias
    libgbm1 \
    curl \
    unzip \
    fonts-liberation \
    libu2f-udev \
    libvulkan1 \
    xdg-utils \
    && rm -rf /var/lib/apt/lists/*

# Configurar variables de entorno
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMIUM_BIN=/usr/bin/chromium
ENV DISPLAY=:99
ENV CHROME_PATH=/usr/lib/chromium/

# Crear directorios necesarios
WORKDIR /app
RUN mkdir -p /app/src/main/java/com/example/PruebaCRUD/Scraping/images/
RUN chmod -R 777 /app
# Agregar permisos para la cache de Selenium
RUN mkdir -p /root/.cache && chmod -R 777 /root/.cache

# Copiar el JAR
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Script de inicio modificado con más tiempo de espera
RUN echo '#!/bin/bash\nXvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\nsleep 2\n/usr/bin/google-chrome --version\nchromium --version\nexec java -Dwebdriver.chrome.whitelistedIps="" -Xmx256m -Xms128m -jar /app.jar' > /start.sh
RUN chmod +x /start.sh

# Ejecutar el script de inicio
CMD ["/start.sh"]