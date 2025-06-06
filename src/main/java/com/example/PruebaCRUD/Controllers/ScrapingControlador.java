package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.CredencialRespuestaDTO;
import com.example.PruebaCRUD.Scraping.Scraping;
import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import com.example.PruebaCRUD.Services.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;


/**
 * Clase API que tendrá los endpoints
 */
@RestController
public class ScrapingControlador {
    @Autowired
    private AlumnoServicio alumnoService;

    private final Scraping scraping = new Scraping();

    @GetMapping("/ImagePDF") // Notación para manejar solicitudes GET
    public ResponseEntity<FileSystemResource> obtenerImagenCalendario() {
        try {
            // URL del sitio web y selector CSS
            String url = "https://www.ipn.mx/calendario-academico.html";
            String cssQuery = ".col-12.col-md-4.offset-md-1 a";

            // Procesar el PDF y obtener la ruta de la imagen
            String imagePath = scraping.procesarPdfAImage(url, cssQuery);

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
    public ResponseEntity<CredencialRespuestaDTO> capturarCredencial(
            @RequestParam("url") String credencialUrl) {
        System.out.println("=== SOLICITUD RECIBIDA ===");
        System.out.println("URL recibida: " + credencialUrl);

        try {
            System.out.println("Iniciando proceso de scraping...");
            Map<String, String> scrapingResult = ScrapingCredencial.capturarCredencial(credencialUrl);

            System.out.println("Resultados del scraping:");
            scrapingResult.forEach((k, v) -> System.out.println(k + ": " + v));

            String boleta = scrapingResult.get("boleta");
            String imagenPath = scrapingResult.get("imagenPath");

            if (boleta == null || boleta.isEmpty()) {
                System.err.println("No se encontró la boleta en el HTML");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new CredencialRespuestaDTO("No se encontró la boleta en el HTML", null));
            }

            if (imagenPath == null) {
                System.err.println("No se generó la ruta de la imagen");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new CredencialRespuestaDTO("Error al generar la imagen (ruta no encontrada)", null));
            }

            System.out.println("Procesando imagen...");
            String fullImagePath = ScrapingCredencial.ALMACENAMIENTO_RUTA_COMPLETA +
                    imagenPath.substring(ScrapingCredencial.PREFIJO_FRONTEND_RUTA_IMAGEN.length());

            String base64Image = convertirImagenABase64(fullImagePath);

            System.out.println("Buscando credenciales en la base de datos...");
            List<CredencialDTO> credenciales = alumnoService.encontrarCredencialPorBoleta(boleta);

            System.out.println("Proceso completado exitosamente");
            CredencialRespuestaDTO responseDTO = new CredencialRespuestaDTO(base64Image, credenciales);
            return ResponseEntity.ok().body(responseDTO);

        } catch (IOException e) {
            System.err.println("=== ERROR DURANTE EL PROCESO ===");
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CredencialRespuestaDTO("Error al procesar la credencial: " + e.getMessage(), null));
        }
    }

    private String convertirImagenABase64(String imagePath) throws IOException {
        try {
            File imageFile = new File(imagePath);
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.err.println("Error al convertir imagen a Base64: " + e.getMessage());
            throw e;
        }
    }
}
