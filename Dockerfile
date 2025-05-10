# Etapa 1: Compilación con Maven
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de producción basada en imagen oficial de Playwright
FROM mcr.microsoft.com/playwright/java:v1.42.0-focal

# Instalar dependencias adicionales si son necesarias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    fonts-liberation \
    libappindicator3-1 \
    xvfb \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Configurar entorno
ENV DISPLAY=:99
ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Crear directorio para imágenes persistentes
RUN mkdir -p /home/site/wwwroot/images/credenciales && \
    chmod -R 777 /home/site/wwwroot/images

# Puerto expuesto
EXPOSE 8080

# Comando de inicio optimizado
ENTRYPOINT ["java", "-jar", "app.jar"]
