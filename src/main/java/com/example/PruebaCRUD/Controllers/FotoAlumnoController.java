package com.example.PruebaCRUD.Controllers;


import com.example.PruebaCRUD.DTO.FotoAlumnoDTO;
import com.example.PruebaCRUD.Services.FotoAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
@RequestMapping("/InfoA")
public class FotoAlumnoController {

    @Autowired
    private FotoAlumnoService fotoAlumnoService;

    @GetMapping("/foto/{boleta}")
    public ResponseEntity<byte[]> getFotoAlumno(@PathVariable String boleta) {
        try {
            // 1. Obtener la información del alumno y su imagen desde Django
            FotoAlumnoDTO fotoAlumnoDTO = fotoAlumnoService.obtenerFotoPorBoleta(boleta);

            // 2. Leer los bytes de la imagen obtenida desde Django
            byte[] imageBytes = fotoAlumnoService.obtenerImagenDesdeDjango(fotoAlumnoDTO.getFotoUrl());

            // 3. Enviar la imagen a Android
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Ajusta si es otro tipo de imagen
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}


