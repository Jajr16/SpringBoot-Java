package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.CredencialResponseDTO;
import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import com.example.PruebaCRUD.Services.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

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
            // Capturar la imagen y guardarla en el servidor
            String imagePath = ScrapingCredencial.capturarCredencial(credencialUrl);
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Extraer la boleta de la imagen
            String textoExtraido = ScrapingCredencial.extraerTextoDeImagen(new File(imagePath));
            String boleta = ScrapingCredencial.buscarBoletaEnTexto(textoExtraido);

            // Obtener la información del alumno usando la boleta
            List<CredencialDTO> credenciales = credencialService.findCredencialPorBoleta(boleta);

            // Crear la respuesta
            CredencialResponseDTO response = new CredencialResponseDTO(base64Image, credenciales);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error en el scraping: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}