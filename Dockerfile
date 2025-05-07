#--------------------------------------------------------------------
# Etapa de Construcción (Build Stage)
#--------------------------------------------------------------------
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY . /app/

# Compilar el proyecto
# Asegúrate que tu pom.xml ahora NO tiene las exclusiones de slf4j-api
RUN mvn clean package -DskipTests

#--------------------------------------------------------------------
# Etapa Final (Final Stage)
#--------------------------------------------------------------------
FROM openjdk:17-jdk-slim

LABEL maintainer="tu-email@example.com"

# Definir la versión de Chrome y ChromeDriver a utilizar (busca la última estable en https://googlechromelabs.github.io/chrome-for-testing/)
# Ejemplo: para una versión reciente (¡verifica la última disponible!)
ENV CHROME_VERSION="124.0.6367.201"

# Instalar dependencias necesarias para Chrome y otras herramientas
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    gnupg2 \
    unzip \
    curl \
    xvfb \
    # Dependencias mínimas para Chrome headless. Ajusta si es necesario.
    # Muchas de las que listaste son para GUI completas. Para headless, se necesitan menos.
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libgbm1 \
    libgtk-3-0 \
    # libx11-6 \ # Generalmente no necesarias para headless puro con --no-sandbox
    # ... considera reducir la lista de dependencias de GUI si es posible ...
    fonts-liberation \
    # Limpiar caché de apt
    && rm -rf /var/lib/apt/lists/*

# Instalar Chrome para Testing (desde el repositorio oficial de chrome-for-testing)
RUN wget "https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chrome-linux64.zip" -O /tmp/chrome-linux64.zip && \
    unzip /tmp/chrome-linux64.zip -d /opt/ && \
    mv /opt/chrome-linux64 /opt/chrome && \
    rm /tmp/chrome-linux64.zip && \
    # Crear enlace simbólico para que coincida con CHROME_BIN si es necesario o simplemente usa /opt/chrome/chrome
    ln -s /opt/chrome/chrome /usr/bin/google-chrome-stable

# Instalar ChromeDriver para Testing (misma versión que Chrome)
RUN wget "https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64/chromedriver-linux64.zip" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /opt/chromedriver-temp && \
    mv /opt/chromedriver-temp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    rm -rf /tmp/chromedriver.zip /opt/chromedriver-temp && \
    chmod +x /usr/local/bin/chromedriver

# Copiar el JAR de la aplicación desde la etapa de build
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar /home/site/wwwroot/app.jar

# Variables de entorno para la aplicación y Selenium
ENV CHROME_BIN=/usr/bin/google-chrome-stable
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
ENV DISPLAY=:99
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Djava.io.tmpdir=/home/site/wwwroot/app_tmp"
ENV PORT=8080

# Crear directorios necesarios para la aplicación y para datos temporales de Chrome
# /home/site/wwwroot es el directorio de trabajo y persistente en Azure App Service
RUN mkdir -p /home/site/wwwroot/images/credenciales && \
    mkdir -p /home/site/wwwroot/app_tmp && \
    mkdir -p /home/site/wwwroot/chrome_data && \
    # Ajustar permisos. App Service generalmente ejecuta como un usuario no root.
    # Dar permisos al usuario que ejecutará la aplicación (Azure suele usar un UID/GID específico o uno del rango no privilegiado)
    # Por ahora, hacemos que sea escribible por todos dentro de /home/site/wwwroot, que es común en App Service.
    chmod -R 777 /home/site/wwwroot

# Puerto que la aplicación expone DENTRO del contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación
# Azure usará este ENTRYPOINT o el "Comando de inicio" que configures en el portal.
# Es mejor definir el ENTRYPOINT aquí.
ENTRYPOINT ["java", "-jar", "/home/site/wwwroot/app.jar"]