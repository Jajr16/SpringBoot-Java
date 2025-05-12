# ========= ETAPA DE CONSTRUCCIÓN =========
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# ========= ETAPA DE EJECUCIÓN =========
FROM eclipse-temurin:17-jdk-jammy

# --- 1. Instalar solo dependencias necesarias para PDFBox ---
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    libfreetype6 \
    fontconfig \
    fonts-dejavu \
    fonts-liberation \
    fonts-noto \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# --- 2. Configuración de la aplicación ---
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# --- 3. Crear directorios necesarios ---
RUN mkdir -p /app/images/credenciales && \
    mkdir -p /app/pdfs/credenciales && \
    mkdir -p /app/fonts

# --- 4. Variables de entorno importantes ---
ENV JAVA_OPTS="-Djava.awt.headless=true -Xmx512m"
ENV PERSISTENT_STORAGE_BASE=/app/
ENV PDF_DPI=300

# --- 5. Permisos y usuario ---
RUN groupadd -r appuser && \
    useradd -r -g appuser appuser && \
    chown -R appuser:appuser /app

USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]