package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Services.ListETSService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ETS/InscripcionAlumno")
public class ListETSController {
    private final ListETSService listETSService;

    public ListETSController(ListETSService listETSService) {
        this.listETSService = listETSService;
    }

    @GetMapping("/{boleta}")
    public ResponseEntity<ListETSResponseDTO> inscripList(@PathVariable("boleta") String boleta) {
        System.out.println("AQUÍ ESTÁ LA BOLETA " + boleta);
        ListETSResponseDTO response = this.listETSService.inscripcionesETS(boleta);
        return ResponseEntity.ok(response);
    }
}
