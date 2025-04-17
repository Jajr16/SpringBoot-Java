FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    ffmpeg \
    libx264-dev \
    && rm -rf /var/lib/apt/lists/*

COPY target/PruebaCRUD-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]