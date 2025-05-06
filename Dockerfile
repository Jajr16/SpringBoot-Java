# Etapa 1: Compilación con Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests
RUN ls -la /app/target/

# Etapa 2: Imagen de producción
FROM openjdk:17-jdk-slim

# Configuración para Azure
ENV WEBSITES_PORT=8080
EXPOSE 8080

# Variables de entorno esenciales
ENV DEBIAN_FRONTEND=noninteractive
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
ENV DISPLAY=:99

# Instalar dependencias mínimas + Chrome
RUN apt-get update && apt-get install -y \
    wget gnupg unzip xvfb \
    libgtk-3-0 libgbm1 libnss3 libxss1 libasound2 \
    fonts-liberation && \
    rm -rf /var/lib/apt/lists/*

# Instalar Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    rm -rf /var/lib/apt/lists/*

# Instalar ChromeDriver compatible
RUN CHROME_VERSION=$(google-chrome-stable --version | awk '{print $3}') && \
    wget -q "https://chromedriver.storage.googleapis.com/${CHROME_VERSION}/chromedriver_linux64.zip" && \
    unzip chromedriver_linux64.zip && \
    mv chromedriver /usr/local/bin/ && \
    chmod +x /usr/local/bin/chromedriver && \
    rm chromedriver_linux64.zip

# Configurar directorio de la app
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN chmod +r app.jar

# Script de inicio optimizado para Azure
RUN echo '#!/bin/bash\n\
echo "=== INICIALIZANDO CONTENEDOR ==="\n\
echo "Versiones:"\n\
java -version\n\
google-chrome-stable --version\n\
chromedriver --version\n\
echo "=== INICIANDO Xvfb ==="\n\
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\n\
sleep 5\n\
echo "=== INICIANDO APLICACIÓN EN PUERTO $WEBSITES_PORT ==="\n\
exec java -Dserver.port=$WEBSITES_PORT \
          -Dwebdriver.chrome.driver=$CHROMEDRIVER_PATH \
          -jar /app/app.jar' > /start.sh && \
    chmod +x /start.sh

# Health check para Azure
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:$WEBSITES_PORT/actuator/health || exit 1

CMD ["/start.sh"]