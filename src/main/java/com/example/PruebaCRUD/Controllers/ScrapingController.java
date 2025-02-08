package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Scraping.Scraping;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/ImagePDF") // Mapear la url a este método
public class ScrapingController {
    private final Scraping scraping = new Scraping();

    @GetMapping // Notación para manejar solicitudes GET
    public ResponseEntity<FileSystemResource> getCalendarImage() {
        try {
            // URL del sitio web y selector CSS
            String url = "https://www.ipn.mx/calendario-academico.html";
            String cssQuery = ".col-12.col-md-4.offset-md-1 a";

            // Procesar el PDF y obtener la ruta de la imagen
            String imagePath = scraping.processPdfToImage(url, cssQuery);

            // Retornar la imagen como respuesta
            File imageFile = new File(imagePath);
            FileSystemResource resource = new FileSystemResource(imageFile);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=calendario.png")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }
}
