# Etapa 1: construir la aplicación
FROM maven:3.9.6-eclipse-temurin-17-alpine as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: crear la imagen final
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]


ENV DEBIAN_FRONTEND=noninteractive
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV SELENIUM_VERSION=4.16.1

# Instalar dependencias del sistema
RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    unzip \
    curl \
    xvfb

# Instalar Chrome específico
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list

RUN apt-get update && apt-get install -y --no-install-recommends \
    google-chrome-stable=136.0.7103.59-1 \
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
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrender1 \
    libpango-1.0-0 \
    fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

# Instalar ChromeDriver específico (debe coincidir con la versión de Chrome)
RUN CHROME_MAJOR_VERSION=$(google-chrome-stable --version | sed -E 's/.* ([0-9]+)\..*/\1/') && \
    CHROME_VERSION=$(google-chrome-stable --version | awk '{print $3}') && \
    echo "Versión de Chrome detectada: $CHROME_VERSION" && \
    wget "https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chromedriver-linux64.zip" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /opt/chromedriver && \
    chmod +x /opt/chromedriver/chromedriver-linux64/chromedriver && \
    ln -s /opt/chromedriver/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    rm /tmp/chromedriver.zip

# Configurar variables de entorno
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
ENV DISPLAY=:99

# Crear directorios necesarios
RUN mkdir -p /app/src/main/resources/static/images/credenciales && \
    chmod -R 777 /app && \
    mkdir -p /root/.cache && \
    chmod -R 777 /root/.cache

# Copiar el JAR de la aplicación
COPY --from=build /app/target/*.jar /app/app.jar
RUN chmod +r /app/app.jar

# Script de inicio mejorado
RUN echo '#!/bin/bash\n\
echo "=== INICIO DE CONFIGURACIÓN ==="\n\
echo "Versión de Chrome:"\n\
google-chrome-stable --version\n\
echo "Versión de ChromeDriver:"\n\
chromedriver --version\n\
echo "Verificando archivo JAR... de Spring"\n\
if [ ! -f /app/app.jar ]; then\n\
  echo "ERROR: No se encuentra /app/app.jar"\n\
  echo "Contenido de /app:"\n\
  ls -la /app\n\
  exit 1\n\
fi\n\
echo "Tamaño del JAR: $(du -h /app/app.jar | cut -f1)"\n\
echo "Iniciando Xvfb..."\n\
Xvfb :99 -screen 0 1920x1080x24 -ac +extension GLX +render -noreset &\n\
sleep 5\n\
echo "Variables de entorno:"\n\
echo "CHROME_BIN=$CHROME_BIN"\n\
echo "CHROMEDRIVER_PATH=$CHROMEDRIVER_PATH"\n\
echo "DISPLAY=$DISPLAY"\n\
echo "=== INICIANDO APLICACIÓN ==="\n\
java -Dwebdriver.chrome.driver=$CHROMEDRIVER_PATH \
     -Dselenium.version=$SELENIUM_VERSION \
     -Dwebdriver.chrome.verboseLogging=true \
     -Xmx512m -Xms256m \
     -jar /app/app.jar' > /start.sh && \
chmod +x /start.sh

EXPOSE 8080

CMD ["/start.sh"]