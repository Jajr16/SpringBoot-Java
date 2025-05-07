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

# Configuración de versiones
ENV CHROME_VERSION="latest"
ENV CHROMEDRIVER_VERSION="latest"

# Configuración crítica para Selenium
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
ENV DISPLAY=:99
ENV LANG=C.UTF-8

# Configuración de directorios persistentes
RUN mkdir -p /home/site/wwwroot/images/credenciales && \
    mkdir -p /home/site/wwwroot/chrome_data && \
    chmod -R 777 /home/site/wwwroot

# Limpieza total de cachés y configuración de enlaces
RUN rm -rf /root/.cache/selenium && \
    mkdir -p /root/.cache/selenium/chromedriver/linux64 && \
    ln -sf /usr/local/bin/chromedriver /root/.cache/selenium/chromedriver/linux64/chromedriver

# 1. Instalar dependencias del sistema (incluyendo unzip)
RUN apt-get update && apt-get install -y --no-install-recommends \
    ca-certificates \
    gnupg \
    curl \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

# 2. Configurar repositorio de Chrome
RUN curl -sSL https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list

# 3. Instalar Chrome y dependencias
RUN apt-get update && apt-get install -y --no-install-recommends \
    google-chrome-stable \
    xvfb \
    libglib2.0-0 libnss3 libgconf-2-4 libfontconfig1 \
    libx11-6 libx11-xcb1 libxcb1 libxcomposite1 \
    libxcursor1 libxdamage1 libxext6 libxfixes3 \
    libxi6 libxrandr2 libxrender1 libxss1 libxtst6 \
    libgbm1 libasound2 libatk1.0-0 libatk-bridge2.0-0 \
    libcups2 libdrm2 libpango-1.0-0 libcairo2 \
    libgtk-3-0 libxkbcommon0 fonts-liberation \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# 4. Instalar ChromeDriver compatible (solución robusta)
RUN set -ex; \
    CHROMEDRIVER_RELEASE=$(curl -sS https://chromedriver.storage.googleapis.com/LATEST_RELEASE); \
    wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_RELEASE/chromedriver_linux64.zip"; \
    unzip /tmp/chromedriver.zip -d /usr/local/bin/; \
    chmod +x /usr/local/bin/chromedriver; \
    rm /tmp/chromedriver.zip; \
    chromedriver --version

# Verificación exhaustiva
RUN echo "=== Versiones instaladas ===" \
    && google-chrome-stable --version \
    && chromedriver --version \
    && echo "=== Ubicaciones ===" \
    && ls -la /usr/bin/google-chrome* \
    && ls -la /usr/local/bin/chromedriver* \
    && echo "=== Cache Selenium ===" \
    && ls -la /root/.cache/selenium/chromedriver/linux64/

# Copiar aplicación
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar /app.jar

# Configuración de salud
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]