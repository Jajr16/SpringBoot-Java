package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DetalleETSDTO;
import com.example.PruebaCRUD.DTO.TiempoParaETS;
import com.example.PruebaCRUD.Services.ETSServicio;
import com.example.PruebaCRUD.Services.PeriodoETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP) // Mapear la url a este método
public class DetallesETSControlador {
    private final ETSServicio etsServicio;
    private final PeriodoETSServicio periodoETSServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public DetallesETSControlador(ETSServicio etsServicio, PeriodoETSServicio periodoETSServicio) {
        this.etsServicio = etsServicio;
        this.periodoETSServicio = periodoETSServicio;
    }

    @GetMapping("/SalonETS/{ets}") // Notación para manejar solicitudes GET
    public ResponseEntity<DetalleETSDTO> detallesETS(@PathVariable("ets") Integer ets) {
        DetalleETSDTO respuesta = this.etsServicio.detallesETS(ets);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/TimeToETS") // Notación para manejar solicitudes GET
    public TiempoParaETS obtenerFechaETS() {
        return this.periodoETSServicio.obtenerTiempoParaETS();
    }
}
