package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Services.ListETSService;
import com.example.PruebaCRUD.Services.ListETSService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("ETS/InscripcionDocente") // Mapear la url a este método
public class ListInscripcionesController2 {
    private final ListETSService2 listETSService2;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public ListInscripcionesController2(ListETSService2 listETSService2) {
        this.listETSService2 = listETSService2;
    }

    @GetMapping("/{docente_rfc}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListETSResponseDTO>> inscripList(@PathVariable("docente_rfc") String boleta) {
        List<ListETSResponseDTO> response = listETSService2.inscripcionesETS(boleta);

        return ResponseEntity.ok(response);
    }
}
