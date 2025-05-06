package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.CredencialResponseDTO;
import com.example.PruebaCRUD.Scraping.Scraping;
import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import com.example.PruebaCRUD.Services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
public class ScrapingController {
    @Autowired
    private AlumnoService alumnoService;

    private final Scraping scraping = new Scraping();

    @GetMapping("/ImagePDF") // Notación para manejar solicitudes GET
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

    @GetMapping("/ImageDAE/capturar")
    public ResponseEntity<CredencialResponseDTO> capturarCredencial(
            @RequestParam("url") String credencialUrl) {
        System.out.println("Solicitud recibida para capturar credencial: " + credencialUrl);

        try {
            // Paso 1: Extraer boleta del HTML y capturar screenshot
            Map<String, String> scrapingResult = ScrapingCredencial.capturarCredencial(credencialUrl);
            String boleta = scrapingResult.get("boleta");
            String imagePath = scrapingResult.get("imagenPath");

            if (boleta == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CredencialResponseDTO("No se encontró la boleta en el HTML", null));
            }

            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            List<CredencialDTO> credenciales = alumnoService.findCredencialPorBoleta(boleta);

            CredencialResponseDTO response = new CredencialResponseDTO(base64Image, credenciales);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            System.err.println("Error en el scraping: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CredencialResponseDTO("Error al procesar la credencial: " + e.getMessage(), null));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
