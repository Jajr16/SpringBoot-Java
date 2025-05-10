# ========= ETAPA DE CONSTRUCCIÓN =========
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ========= ETAPA DE EJECUCIÓN =========
FROM mcr.microsoft.com/playwright/java:v1.42.0-jammy

# --- 1. Instalar TODAS las dependencias incluyendo las sugeridas por Playwright ---
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    # Dependencias principales
    fonts-liberation libappindicator3-1 xvfb \
    # Dependencias específicas listadas en el error
    libglib2.0-0 libnss3 libnspr4 libdbus-1-3 \
    libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libdrm2 libatspi2.0-0 libx11-6 libxcomposite1 \
    libxdamage1 libxext6 libxfixes3 libxrandr2 \
    libgbm1 libxcb1 libxkbcommon0 libpango-1.0-0 \
    libcairo2 libasound2 libharfbuzz0b \
    # Dependencias adicionales recomendadas
    libgdk-pixbuf2.0-0 libgtk-3-0 libxtst6 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# --- 2. Configuración crítica de Playwright ---
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
ENV DISPLAY=:99
ENV PLAYWRIGHT_DOWNLOAD_HOST=https://playwright.azureedge.net

# --- 3. Verificación explícita de dependencias ---
RUN echo "=== Verificando dependencias ===" && \
    ldconfig -p | grep -E 'glib|nss|nspr|dbus|atk|cups|drm|x11|gbm|xcb|pango|cairo|asound|harfbuzz|gdk|gtk|xrender|xtst' && \
    echo "=== Navegadores disponibles ===" && \
    ls -la /ms-playwright && \
    echo "=== Chromium instalado ===" && \
    find /ms-playwright -name chrome -exec ls -la {} \;

# --- 4. Preparar entorno de la aplicación ---
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