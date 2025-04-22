package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Services.ListETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
public class ListInscripcionesController {
    private final ListETSService listETSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public ListInscripcionesController(ListETSService listETSService) {
        this.listETSService = listETSService;
    }

    @GetMapping("/ETS/InscripcionAlumno/{boleta}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListETSResponseDTO>> inscripList(@PathVariable("boleta") String boleta) {
        List<ListETSResponseDTO> response = listETSService.inscripcionesETS(boleta);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ETS/InscripcionDocente/{docente_rfc}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListETSResponseDTO>> LitaAplicacion(@PathVariable("docente_rfc") String boleta) {
        List<ListETSResponseDTO> response = listETSService.aplicacionETS(boleta);

        return ResponseEntity.ok(response);
    }
}
