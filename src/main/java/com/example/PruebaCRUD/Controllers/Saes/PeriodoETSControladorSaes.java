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
public class PeriodoETSControladorSaes {
    private final PeriodoETSServicio periodoETSServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public PeriodoETSControladorSaes(PeriodoETSServicio periodoETSServicio) {
        this.periodoETSServicio = periodoETSServicio;
    }

    @GetMapping("/periodoETS")  // Notación para manejar solicitudes GET
    public ResponseEntity<List<periodoETS>> obtenerPeriodos() {
        List<periodoETS> respuesta = this.periodoETSServicio.obtenerPeriodos();
        System.out.println(respuesta);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/periodoETS") // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarPeriodo(@RequestBody periodoETS periodoETS) {
        System.out.println("EL PERIODO QUE DA ES " + periodoETS);
        return this.periodoETSServicio.nuevoPeriodo(periodoETS);
    }


    @GetMapping("/PeriodoToETS")
    public ResponseEntity<List<PeriodosETSProjectionSaes>> obtenerPeriodosParaETS() {
        List<PeriodosETSProjectionSaes> respuesta = this.periodoETSServicio.obtenerPeriodosSAES();

        if (respuesta.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(respuesta);
        return ResponseEntity.ok(respuesta);
    }
}
