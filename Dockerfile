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
    chromium-chromedriver \
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

# Configurar ChromeDriver y directorios necesarios
RUN chmod +x /usr/bin/chromedriver && \
    ln -s /usr/bin/chromedriver /usr/local/bin/chromedriver && \
    mkdir -p /root/.cache/selenium && \
    chmod -R 777 /root/.cache/selenium && \
    mkdir -p /root/.cache/selenium/chromedriver/linux64 && \
    chmod -R 777 /root/.cache/selenium/chromedriver && \
    mkdir -p /tmp/selenium && \
    chmod -R 777 /tmp/selenium

# Configurar variables de entorno
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/root/.cache/selenium/chromedriver/linux64/chromedriver
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
echo "Verificando instalación de Chrome..."\n\
google-chrome-stable --version\n\
echo "Verificando instalación de ChromeDriver..."\n\
chromedriver --version\n\
echo "Iniciando Xvfb..."\n\
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\n\
sleep 5\n\
echo "Verificando permisos de directorios..."\n\
ls -la /app/src/main/resources/static/images/credenciales\n\
ls -la /root/.cache/selenium\n\
ls -la /root/.cache/selenium/chromedriver/linux64\n\
echo "Verificando ChromeDriver..."\n\
find /root/.cache/selenium -name "chromedriver*"\n\
echo "Starting application..."\n\
java -Dwebdriver.chrome.driver=/root/.cache/selenium/chromedriver/linux64/chromedriver \
     -Dwebdriver.chrome.whitelistedIps="" \
     -Dwebdriver.chrome.verboseLogging=true \
     -Xmx512m -Xms256m \
     -jar /app/app.jar' > /start.sh && \
    chmod +x /start.sh

# Verificar que los ejecutables necesarios están disponibles y tienen permisos
RUN ls -la /usr/bin/google-chrome-stable && \
    ls -la /usr/bin/chromedriver && \
    ls -la /usr/local/bin/chromedriver && \
    ls -la /root/.cache/selenium

EXPOSE 8080

CMD ["/start.sh"]