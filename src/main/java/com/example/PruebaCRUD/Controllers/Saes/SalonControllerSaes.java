package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.SalonProjectionSaes;
import com.example.PruebaCRUD.Services.ETSService;
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
public class SalonControllerSaes {
    private final ETSService etsService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public SalonControllerSaes(ETSService etsService) {
        this.etsService = etsService;
    }

    @GetMapping("/SalonToETS") // Notación para manejar solicitudes GET
    public ResponseEntity<List<?>> getSalonToETS() {
        List<?> response = this.etsService.getSalonesToETS();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println("SALON TO ETS ES " + response);
        return ResponseEntity.ok(response);
    }
}
