package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.CredencialResponseDTO;
import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import com.example.PruebaCRUD.Services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ImageDAE")
public class ScrapingCredencialController {

    @Autowired
    private CredencialService credencialService;

    @GetMapping("/capturar")
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

            List<CredencialDTO> credenciales = credencialService.findCredencialPorBoleta(boleta);

            CredencialResponseDTO response = new CredencialResponseDTO(base64Image, credenciales);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (IOException e) {
            System.err.println("Error en el scraping: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CredencialResponseDTO("Error al procesar la credencial: " + e.getMessage(), null));
        }
    }
}