# Etapa 1: Compilación con Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
# Primero descarga las dependencias
RUN mvn dependency:go-offline
COPY src ./src
# Empaqueta la aplicación
RUN mvn clean package -DskipTests
# Verifica que el JAR se creó
RUN ls -la /app/target/

# Etapa 2: Imagen de producción
FROM openjdk:17-jdk-slim

# Variables de entorno
ENV DEBIAN_FRONTEND=noninteractive
ENV CHROME_VERSION="136.0.7103.59"
ENV CHROMEDRIVER_VERSION="136.0.7103.49"
ENV SELENIUM_VERSION="4.16.1"
ENV DISPLAY=":99"
ENV APP_HOME="/app"

# Instalar dependencias del sistema
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    unzip \
    curl \
    xvfb \
    libgtk-3-0 \
    libgbm1 \
    libnss3 \
    libxss1 \
    libasound2 \
    libatk1.0-0 \
    libcairo2 \
    libcups2 \
    libdbus-1-3 \
    libfontconfig1 \
    libfreetype6 \
    libglib2.0-0 \
    libx11-6 \
    libx11-xcb1 \
    libxcb1 \
    fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

# Instalar Chrome específico
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable=$CHROME_VERSION-1 \
    && rm -rf /var/lib/apt/lists/*

# Instalar ChromeDriver específico
RUN wget -q "https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip" \
    && unzip chromedriver_linux64.zip \
    && mv chromedriver /usr/local/bin/ \
    && chmod +x /usr/local/bin/chromedriver \
    && rm chromedriver_linux64.zip

# Configurar directorio de la aplicación
WORKDIR $APP_HOME
RUN mkdir -p /app/logs \
    && chmod -R 777 /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar
RUN chmod +r app.jar

# Verificar que el JAR existe y es válido
RUN if [ ! -f app.jar ]; then \
      echo "ERROR: No se encontró el archivo JAR después de COPY" && exit 1; \
    fi && \
    echo "JAR copiado correctamente. Tamaño:" $(du -h app.jar | cut -f1)

# Script de inicio mejorado
RUN echo '#!/bin/bash\n\
set -x\n\
echo "=== INICIALIZANDO CONTENEDOR === "\n\
echo "Versiones instaladas:"\n\
echo "  Java: $(java -version 2>&1 | head -n 1)"\n\
echo "  Chrome: $(google-chrome-stable --version)"\n\
echo "  ChromeDriver: $(chromedriver --version)"\n\
echo "  JAR: $(ls -la /app/app.jar)"\n\
echo "=== INICIANDO Xvfb ==="\n\
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\n\
sleep 5\n\
echo "=== INICIANDO APLICACIÓN ==="\n\
exec java \
    -Dwebdriver.chrome.driver=/usr/local/bin/chromedriver \
    -Dselenium.version=$SELENIUM_VERSION \
    -Dlogging.level.root=INFO \
    -jar /app/app.jar\n\
' > /start.sh && chmod +x /start.sh

EXPOSE 8080

CMD ["/start.sh"]