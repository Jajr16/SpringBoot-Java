package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DetailETSDTO;
import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Services.ETSService;
import com.example.PruebaCRUD.Services.PeriodoETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP) // Mapear la url a este método
public class ETSDetailsController {
    private final ETSService etsService;
    private final PeriodoETSService periodoETSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public ETSDetailsController(ETSService etsService, PeriodoETSService periodoETSService) {
        this.etsService = etsService;
        this.periodoETSService = periodoETSService;
    }

    @GetMapping("/SalonETS/{ets}") // Notación para manejar solicitudes GET
    public ResponseEntity<DetailETSDTO> detallesETS(@PathVariable("ets") Integer ets) {
        DetailETSDTO response = this.etsService.detallesETS(ets);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/TimeToETS") // Notación para manejar solicitudes GET
    public TimeToETSDTO getTimeToETS() {
        return this.periodoETSService.getTimeToETS();
    }
}
