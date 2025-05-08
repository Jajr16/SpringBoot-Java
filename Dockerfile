#--------------------------------------------------------------------
# Build Stage
#--------------------------------------------------------------------
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
# Primero copiamos solo el POM para cachear las dependencias
RUN mvn dependency:go-offline
COPY src ./src
# Luego el build completo
RUN mvn clean package -DskipTests

#--------------------------------------------------------------------
# Final Stage
#--------------------------------------------------------------------
FROM mcr.microsoft.com/playwright/java:v1.42.0-focal

# Configuración de directorios persistentes (con verificación)
RUN mkdir -p /home/site/wwwroot/images/credenciales && \
    mkdir -p /home/site/wwwroot && \
    chmod -R 777 /home/site/wwwroot && \
    echo "Directorios creados correctamente"

# Instalar solo dependencias adicionales necesarias
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    fonts-liberation \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Copiar aplicación
COPY --from=build /app/target/*.jar /app.jar

# Configuración de salud
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]