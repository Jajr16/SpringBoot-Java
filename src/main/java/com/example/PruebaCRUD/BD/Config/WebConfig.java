package com.example.PruebaCRUD.BD.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // La URL base que usará el frontend para acceder a cualquier imagen generada.
    // Por ejemplo, /images/credenciales/imagen.png
    private final String DYNAMIC_IMAGES_URL_PATH = "/images/";

    // La ubicación física DENTRO DEL CONTENEDOR donde se guardan estas imágenes.
    // "/home/site/wwwroot/" es la raíz del almacenamiento persistente de Azure App Service.
    private final String DYNAMIC_IMAGES_FILE_LOCATION = "file:/home/site/wwwroot/images/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Este manejador sirve archivos desde "/home/site/wwwroot/images/**"
        // cuando la URL solicitada es "/images/**"
        registry.addResourceHandler(DYNAMIC_IMAGES_URL_PATH + "**")
                .addResourceLocations(DYNAMIC_IMAGES_FILE_LOCATION);
    }
}