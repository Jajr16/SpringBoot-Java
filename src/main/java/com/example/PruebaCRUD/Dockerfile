# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Instalar ffmpeg
RUN apt-get update && apt-get install -y ffmpeg

# Crear un directorio en el contenedor para almacenar tu app
WORKDIR /app

# Copiar todos los archivos jar del directorio 'target' al contenedor
COPY target/*.jar app.jar

# Expone el puerto en el que la app va a correr
EXPOSE 8080

# Comando para ejecutar tu aplicación, sin especificar el nombre del .jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]
