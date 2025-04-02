package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.UnidadAcademica;
import com.example.PruebaCRUD.Services.UnidadAcademicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/saes") // Mapear la url a este método
public class UnidadAcademicaController {
    private final UnidadAcademicaService unidadAcademicaService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public UnidadAcademicaController(UnidadAcademicaService unidadAcademicaService) {
        this.unidadAcademicaService = unidadAcademicaService;
    }

    @GetMapping("/UAcademica") // Notación para manejar solicitudes GET
    public List<UnidadAcademica> getUA() {
        return this.unidadAcademicaService.getUA();
    }
}
