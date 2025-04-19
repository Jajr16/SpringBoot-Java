package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.Services.FotoAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/InfoA")
public class FotoAlumnoController {

    @Autowired
    private FotoAlumnoService fotoAlumnoService;

    @GetMapping("/foto/{boleta}")
    public ResponseEntity<Map<String, String>> getFotoAlumno(@PathVariable String boleta) {
        try {
            String fotoUrl = fotoAlumnoService.obtenerUrlPorBoleta(boleta);

            Map<String, String> response = new HashMap<>();
            response.put("fotoUrl", fotoUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}



