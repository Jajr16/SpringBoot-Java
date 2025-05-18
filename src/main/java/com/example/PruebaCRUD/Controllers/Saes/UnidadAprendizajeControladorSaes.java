package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import com.example.PruebaCRUD.Services.UnidadAprendizajeServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class UnidadAprendizajeControladorSaes {

    private final UnidadAprendizajeServicio unidadAprendizajeServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public UnidadAprendizajeControladorSaes(UnidadAprendizajeServicio unidadAprendizajeServicio) {
        this.unidadAprendizajeServicio = unidadAprendizajeServicio;
    }

    @GetMapping("/UAprenToETS") // Notación para manejar solicitudes GET
    public ResponseEntity<List<UnidadAprendizajeProjectionSaes>> obtenerUAprenParaETS() {
        List<UnidadAprendizajeProjectionSaes> response = this.unidadAprendizajeServicio.obtenerUApren();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
