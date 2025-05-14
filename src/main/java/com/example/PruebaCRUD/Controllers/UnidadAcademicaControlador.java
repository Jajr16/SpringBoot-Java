package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.UnidadAcademica;
import com.example.PruebaCRUD.Services.UnidadAcademicaServicio;
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
public class UnidadAcademicaControlador {
    private final UnidadAcademicaServicio unidadAcademicaServicio;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public UnidadAcademicaControlador(UnidadAcademicaServicio unidadAcademicaServicio) {
        this.unidadAcademicaServicio = unidadAcademicaServicio;
    }

    @GetMapping("/UAcademica") // Notación para manejar solicitudes GET
    public List<UnidadAcademica> obtenerUA() {
        return this.unidadAcademicaServicio.obtenerUA();
    }
}
