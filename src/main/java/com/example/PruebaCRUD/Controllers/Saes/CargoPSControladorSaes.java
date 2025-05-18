package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.PersonalSeguridadServicio;
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
public class CargoPSControladorSaes {
    private final PersonalSeguridadServicio cargoPSServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, CargoPSService
    public CargoPSControladorSaes(PersonalSeguridadServicio cargoPSServicio) {
        this.cargoPSServicio = cargoPSServicio;
    }

    @GetMapping("/CargoToPS") // Notación para manejar solicitudes GET
    public List<?> obtenerCargosPS() {
        return this.cargoPSServicio.obtenerCargos();
    }
}
