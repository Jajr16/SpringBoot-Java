package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.Scraping.ScrapingCredencial;
import com.example.PruebaCRUD.Services.DetalleAlumnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class DetalleAlumnosController {
    private final DetalleAlumnosService detalleAlumnosService;

    @Autowired
    public DetalleAlumnosController(DetalleAlumnosService detalleAlumnosService) {
        this.detalleAlumnosService = detalleAlumnosService;
    }

    @GetMapping("/capturar")
    public ResponseEntity<InputStreamResource> capturarCredencial(@RequestParam String url) throws IOException {
        String imagePath = ScrapingCredencial.capturarCredencial(url);
        File imageFile = new File(imagePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }
}
