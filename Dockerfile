# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Java, Chrome y ChromeDriver
FROM openjdk:17-jdk-slim

# Instala dependencias para Chrome con logs
RUN echo "Instalando dependencias del sistema..." && \
    apt-get update && \
    apt-get install -y --no-install-recommends \
    wget \
    gnupg \
    ca-certificates \
    unzip \
    fonts-liberation \
    libappindicator3-1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libc6 \
    libcairo2 \
    libcups2 \
    libdbus-1-3 \
    libexpat1 \
    libfontconfig1 \
    libgbm1 \
    libgcc1 \
    libglib2.0-0 \
    libgtk-3-0 \
    libnspr4 \
    libnss3 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libstdc++6 \
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
    lsb-release \
    xdg-utils \
    && echo "Dependencias instaladas correctamente" && \
    rm -rf /var/lib/apt/lists/* || (echo "Fallo al instalar dependencias"; exit 1)

# Instala Google Chrome con verificación
RUN echo "Instalando Google Chrome..." && \
    wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable && \
    echo "Google Chrome instalado. Versión:" && \
    google-chrome --version && \
    rm -rf /var/lib/apt/lists/* || (echo "Fallo al instalar Chrome"; exit 1)

# Instala ChromeDriver con verificación de versión
RUN echo "Instalando ChromeDriver (intentando la última versión compatible)..." && \
    CHROMEDRIVER_LATEST_URL="https://chromedriver.storage.googleapis.com/LATEST_RELEASE" && \
    echo "Descargando la última versión desde: $CHROMEDRIVER_LATEST_URL" && \
    wget -v "$CHROMEDRIVER_LATEST_URL" -O chromedriver_latest_release.txt && \
    if [ -s chromedriver_latest_release.txt ]; then \
        CHROMEDRIVER_VERSION=$(cat chromedriver_latest_release.txt) && \
        echo "Última versión de ChromeDriver disponible es: $CHROMEDRIVER_VERSION" && \
        CHROMEDRIVER_DOWNLOAD_URL="https://chromedriver.storage.googleapis.com/${CHROMEDRIVER_VERSION}/chromedriver_linux64.zip" && \
        echo "Descargando ChromeDriver desde: $CHROMEDRIVER_DOWNLOAD_URL" && \
        wget -v "$CHROMEDRIVER_DOWNLOAD_URL" -O chromedriver_linux64.zip && \
        if [ -s chromedriver_linux64.zip ]; then \
            echo "Descomprimiendo chromedriver_linux64.zip..." && \
            unzip -o chromedriver_linux64.zip && \
            echo "Moviendo chromedriver a /usr/local/bin/" && \
            mv chromedriver /usr/local/bin/ && \
            echo "Dando permisos de ejecución a /usr/local/bin/chromedriver" && \
            chmod +x /usr/local/bin/chromedriver && \
            echo "ChromeDriver instalado. Versión:" && \
            chromedriver --version && \
            rm chromedriver_linux64.zip chromedriver_latest_release.txt; \
        else \
            echo "Error: No se descargó chromedriver_linux64.zip desde $CHROMEDRIVER_DOWNLOAD_URL"; \
            exit 1; \
        fi; \
    else \
        echo "Error: No se pudo obtener la última versión de ChromeDriver desde $CHROMEDRIVER_LATEST_URL"; \
        exit 1; \
    fi || (echo "Fallo general al instalar ChromeDriver"; exit 1)

# Crea directorio para las imágenes
RUN mkdir -p /app/images/ && \
    chmod 777 /app/images/ && \
    echo "Directorio de imágenes creado en /app/images/"

# Copia el .jar
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Health check para verificar Chrome y ChromeDriver
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD google-chrome --version && chromedriver --version || exit 1

CMD ["java", "-Xmx256m", "-Xms128m", "-jar", "/app.jar"]