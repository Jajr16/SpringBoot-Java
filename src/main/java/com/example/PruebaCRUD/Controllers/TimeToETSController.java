package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import com.example.PruebaCRUD.Services.PeriodoETSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/TimeToETS") // Mapear la url a este método
public class TimeToETSController {
    private final PeriodoETSService periodoETSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public TimeToETSController(PeriodoETSService periodoETSService) {
        this.periodoETSService = periodoETSService;
    }

    @GetMapping // Notación para manejar solicitudes GET
    public TimeToETSDTO getTimeToETS() {
        return this.periodoETSService.getTimeToETS();
    }
}
