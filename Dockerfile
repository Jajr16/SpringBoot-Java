# ========= ETAPA DE CONSTRUCCIÓN =========
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ========= ETAPA DE EJECUCIÓN =========
FROM mcr.microsoft.com/playwright/java:v1.42.0-jammy

# --- 1. Instalar dependencias con FORCE-YES ---
RUN apt-get update && \
    apt-get install -y --no-install-recommends --fix-broken --force-yes \
    fonts-liberation libappindicator3-1 xvfb \
    libglib2.0-0 libnss3 libnspr4 libdbus-1-3 \
    libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libdrm2 libatspi2.0-0 libx11-6 libxcomposite1 \
    libxdamage1 libxext6 libxfixes3 libxrandr2 \
    libgbm1 libxcb1 libxkbcommon0 libpango-1.0-0 \
    libcairo2 libasound2 libharfbuzz0b libgdk-pixbuf2.0-0 \
    libgtk-3-0 libxtst6 libxshmfence1 libxrender1 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# --- 2. Configuración CRÍTICA ---
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright
ENV PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD=1
ENV DISPLAY=:99
ENV PLAYWRIGHT_DISABLE_HOST_CHECK=true

# --- 3. Forzar instalación de dependencias ---
RUN npx playwright install-deps --force || true

# --- 4. Configuración aplicación ---
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# --- 5. Permisos y usuario ---
RUN chmod -R 777 /ms-playwright && \
    chown -R pwuser:pwuser /app /ms-playwright

USER pwuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]