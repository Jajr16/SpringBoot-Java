package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.CargoPSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class CargoPSControllerSaes {
    private final CargoPSService cargoPSService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, CargoPSService
    public CargoPSControllerSaes(CargoPSService cargoPSService) {
        this.cargoPSService = cargoPSService;
    }

    @GetMapping("/CargoToPS") // Notación para manejar solicitudes GET
    public List<?> getCargosPS() {
        return this.cargoPSService.getCargos();
    }
}
