package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/ImageDAE")
public class ScrapingCredencialController {

    @GetMapping("/capturar")
    public ResponseEntity<byte[]> capturarCredencial(@RequestParam("url") String credencialUrl) {
        System.out.println("Solicitud recibida para capturar credencial: " + credencialUrl);

        try {
            String imagePath = ScrapingCredencial.capturarCredencial(credencialUrl);
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error en el scraping: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
