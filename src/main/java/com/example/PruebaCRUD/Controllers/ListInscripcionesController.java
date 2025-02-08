package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Services.ListETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("ETS/InscripcionAlumno") // Mapear la url a este método
public class ListInscripcionesController {
    private final ListETSService listETSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public ListInscripcionesController(ListETSService listETSService) {
        this.listETSService = listETSService;
    }

    @GetMapping("/{boleta}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListETSResponseDTO>> inscripList(@PathVariable("boleta") String boleta) {
        List<ListETSResponseDTO> response = listETSService.inscripcionesETS(boleta);

        return ResponseEntity.ok(response);
    }
}
