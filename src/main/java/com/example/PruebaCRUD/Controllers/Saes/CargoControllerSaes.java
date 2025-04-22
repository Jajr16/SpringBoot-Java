package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.DocenteService;
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
    private final DocenteService docenteService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, DocenteService
    public CargoControllerSaes(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping("/CargoToDocente")
    public List<?> getCargos() {
        return this.docenteService.getCargos();
    }
}
