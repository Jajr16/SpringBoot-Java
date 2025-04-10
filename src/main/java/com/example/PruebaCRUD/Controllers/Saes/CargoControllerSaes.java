package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.CargoService;
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
public class CargoControllerSaes {
    private final CargoService cargoService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, CargoService
    public CargoControllerSaes(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/CargoToDocente")
    public List<?> getCargos() {
        return this.cargoService.getCargos();
    }
}
