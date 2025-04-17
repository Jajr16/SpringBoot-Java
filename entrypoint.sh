#!/bin/bash
chmod -R 777 /data
java -jar /app.jar --spring.profiles.active=prod --app.ruta.base=/data