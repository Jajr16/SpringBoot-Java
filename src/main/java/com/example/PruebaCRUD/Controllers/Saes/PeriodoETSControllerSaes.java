package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class PeriodoETSControllerSaes {
    private final PeriodoETSService periodoETSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public PeriodoETSControllerSaes(PeriodoETSService periodoETSService) {
        this.periodoETSService = periodoETSService;
    }

    @GetMapping("/periodoETS")  // Notación para manejar solicitudes GET
    public ResponseEntity<List<periodoETS>> getPeriodos() {
        List<periodoETS> response = this.periodoETSService.getPeriodos();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/periodoETS") // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarPeriodo(@RequestBody periodoETS periodoETS) {
        System.out.println("EL PERIODO QUE DA ES " + periodoETS);
        return this.periodoETSService.newPeriodo(periodoETS);
    }


    @GetMapping("/PeriodoToETS")
    public ResponseEntity<List<PeriodosETSProjectionSaes>> getPeriodosToETS() {
        List<PeriodosETSProjectionSaes> response = this.periodoETSService.obtenerPeriodos();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
