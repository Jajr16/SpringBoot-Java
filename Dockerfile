# Etapa 1: Compilación con Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de producción
FROM openjdk:17-jdk-slim

# Instalar dependencias necesarias para Playwright
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    wget \
    unzip \
    libglib2.0-0 \
    libnss3 \
    libnspr4 \
    libatk1.0-0 \
    libatk-bridge2.0-0 \
    libcups2 \
    libdrm2 \
    libxcb1 \
    libxkbcommon0 \
    libx11-6 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxrandr2 \
    libgbm1 \
    libasound2 \
    libatspi2.0-0 \
    fonts-noto-color-emoji \
    && rm -rf /var/lib/apt/lists/*

# Crear directorio para imágenes persistentes
RUN mkdir -p /home/site/wwwroot/images/credenciales && \
    chmod 777 /home/site/wwwroot/images/credenciales

# Copiar el JAR compilado
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Puerto expuesto (necesario para Azure App Service)
EXPOSE 8080

# Comando de inicio con parámetros para Playwright
CMD java -Djdk.tls.client.protocols=TLSv1.2 \
         -Dplaywright.browsers=chromium \
         -jar /app.jar