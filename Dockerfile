# Etapa 1: Compilar el proyecto
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:17-jdk-slim

ENV DEBIAN_FRONTEND=noninteractive
ENV CHROME_BIN=/usr/bin/google-chrome-stable

RUN apt-get update && apt-get install -y \
    wget \
    gnupg2 \
    apt-transport-https \
    ca-certificates \
    software-properties-common \
    unzip \
    curl

RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor > /usr/share/keyrings/google-chrome.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list

RUN apt-get update && apt-get install -y --no-install-recommends \
    google-chrome-stable \
    xvfb \
    libgtk-3-0 \
    libgbm1 \
    libnss3 \
    libxss1 \
    libasound2 \
    libatk1.0-0 \
    libcairo2 \
    libcups2 \
    libdbus-1-3 \
    libexpat1 \
    libfontconfig1 \
    libfreetype6 \
    libglib2.0-0 \
    libx11-6 \
    libx11-xcb1 \
    libxcb1 \
    libxcomposite1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrender1 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    fonts-liberation \
    xdg-utils \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Obtener la versión de Chrome instalada y descargar el ChromeDriver correspondiente
#RUN wget "https://storage.googleapis.com/chrome-for-testing-public/136.0.7103.49/linux64/chromedriver-linux64.zip" -O /tmp/chromedriver.zip && \
#    unzip /tmp/chromedriver.zip -d /opt/chromedriver && \
#    chmod +x /opt/chromedriver/chromedriver-linux64/chromedriver && \
#    ln -s /opt/chromedriver/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
#    rm /tmp/chromedriver.zip
#
#ENV CHROME_BIN=/usr/bin/google-chrome-stable
#ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver
#ENV DISPLAY=:99
#
#RUN mkdir -p /app/src/main/resources/static/images/credenciales && \
#    chmod -R 777 /app && \
#    mkdir -p /root/.cache && \
#    chmod -R 777 /root/.cache

WORKDIR /home/site/wwwroot
COPY --from=build /app/target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

# Ya no necesitamos el script de inicio ni el comando CMD
# RUN echo ... > /start.sh && chmod +x /start.sh
# CMD ["/start.sh"]

EXPOSE 8080