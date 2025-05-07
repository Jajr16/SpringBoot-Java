#--------------------------------------------------------------------
# Build Stage
#--------------------------------------------------------------------
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY . /app/

RUN mvn clean package -DskipTests

#--------------------------------------------------------------------
# Final Stage
#--------------------------------------------------------------------
FROM openjdk:17-jdk-slim

LABEL maintainer="tu-email@example.com"

# Configuración de logging y versiones
ENV LOGGING_LEVEL=DEBUG
ENV LOGGING_PATTERN="%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
ENV CHROME_VERSION="125.0.6422.76"
ENV CHROMEDRIVER_VERSION="125.0.6422.76"

RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    libasound2 \
    gnupg2 \
    unzip \
    curl \
    xvfb \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libgbm1 \
    libgtk-3-0 \
    fonts-liberation \
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
    procps \
    lsof \
    && rm -rf /var/lib/apt/lists/*

# Crear el enlace simbólico
RUN ln -s /usr/lib/x86_64-linux-gnu/libglib-2.0.so.0 /usr/lib/x86_64-linux-gnu/libglib-2.0.so


# Script de verificación de versiones
RUN echo '#!/bin/sh\n\
echo "=============================================="\n\
echo "  VERIFICACIÓN DE DEPENDENCIAS Y VERSIONES"\n\
echo "=============================================="\n\
echo "\n[Versiones instaladas]"\n\
echo "- Java:"; java -version 2>&1\n\
echo "- Chrome:"; /usr/bin/google-chrome-stable --version\n\
echo "- ChromeDriver:"; /usr/local/bin/chromedriver --version\n\
echo "\n[Variables de entorno]"\n\
echo "CHROME_VERSION=${CHROME_VERSION}"\n\
echo "CHROMEDRIVER_VERSION=${CHROMEDRIVER_VERSION}"\n\
echo "CHROME_BIN=${CHROME_BIN}"\n\
echo "CHROMEDRIVER_PATH=${CHROMEDRIVER_PATH}"\n\
echo "\n[Verificación de bibliotecas]"\n\
echo "Bibliotecas requeridas por Chrome:"\n\
ldd /opt/chrome/chrome | grep -i "not found" || echo "Todas las bibliotecas están presentes"\n\
echo "=============================================="\n\
' > /usr/local/bin/check-versions.sh && \
    chmod +x /usr/local/bin/check-versions.sh

# Instalar Chrome
RUN wget "https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chrome-linux64.zip" -O /tmp/chrome-linux64.zip && \
    unzip /tmp/chrome-linux64.zip -d /opt/ && \
    mv /opt/chrome-linux64 /opt/chrome && \
    rm /tmp/chrome-linux64.zip && \
    ln -s /opt/chrome/chrome /usr/bin/google-chrome-stable && \
    /usr/bin/google-chrome-stable --version || (echo "❌ Error en instalación de Chrome"; exit 1)

# Instalar ChromeDriver
RUN wget "https://storage.googleapis.com/chrome-for-testing-public/${CHROMEDRIVER_VERSION}/linux64/chromedriver-linux64.zip" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /opt/chromedriver-temp && \
    mv /opt/chromedriver-temp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    rm -rf /tmp/chromedriver.zip /opt/chromedriver-temp && \
    chmod +x /usr/local/bin/chromedriver && \
    /usr/local/bin/chromedriver --version || (echo "❌ Error en instalación de ChromeDriver"; exit 1)

# Copiar aplicación
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar /home/site/wwwroot/app.jar

# Configuración de entorno
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
ENV DISPLAY=:99
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Djava.io.tmpdir=/home/site/wwwroot/app_tmp -Dlogging.level.root=${LOGGING_LEVEL} -Dlogging.pattern.console=${LOGGING_PATTERN}"
ENV PORT=8080

# Preparar directorios
RUN mkdir -p /var/log/app && \
    mkdir -p /home/site/wwwroot/images/credenciales && \
    mkdir -p /home/site/wwwroot/app_tmp && \
    mkdir -p /home/site/wwwroot/chrome_data && \
    chmod -R 777 /home/site/wwwroot && \
    chmod -R 777 /var/log/app

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD /usr/local/bin/chromedriver --version && /usr/bin/google-chrome-stable --version || exit 1

EXPOSE 8080

# Iniciar aplicación con verificación
ENTRYPOINT ["sh", "-c", "/usr/local/bin/check-versions.sh && java ${JAVA_OPTS} -jar /home/site/wwwroot/app.jar"]