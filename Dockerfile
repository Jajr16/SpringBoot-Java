# ========= ETAPA DE CONSTRUCCIÓN =========
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ========= ETAPA DE EJECUCIÓN =========
FROM mcr.microsoft.com/playwright/java:v1.42.0-jammy

# --- 1. Instalar dependencias EXACTAS ---
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    fonts-liberation libappindicator3-1 xvfb \
    libglib2.0-0 libnss3 libnspr4 libdbus-1-3 \
    libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libdrm2 libx11-6 libxcb1 libxcomposite1 \
    libxdamage1 libxext6 libxfixes3 libxrandr2 \
    libgbm1 libasound2 libpangocairo-1.0-0 \
    libcairo2 libxkbcommon0 libharfbuzz0b \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# --- 2. Configuración CRÍTICA ---
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
ENV DISPLAY=:99
ENV PLAYWRIGHT_DOWNLOAD_HOST=https://playwright.azureedge.net

# --- 3. Configurar navegadores ---
RUN echo "=== Contenido de /ms-playwright ===" && \
    ls -la /ms-playwright && \
    echo "=== Enlaces simbólicos ===" && \
    ls -la /ms-playwright/.links && \
    echo "=== Ruta exacta de Chromium ===" && \
    find /ms-playwright -name chrome -type f -exec ls -la {} \;

# --- 4. Preparar entorno de la app ---
RUN mkdir -p /app/images/credenciales && \
    chmod -R 777 /app && \
    chown -R 1000:1000 /app

# --- 5. Copiar aplicación ---
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# --- 6. Configuración final ---
USER 1000
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]