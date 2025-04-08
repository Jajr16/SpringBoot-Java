package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.Services.SexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "api/sexo") // Mapear la url a este método
public class SexoController {
    private final SexoService sexoService;

    @Autowired // Notación que permite inyectar dependencias, en este caso, PeriodoETSService
    public SexoController(SexoService sexoService) {
        this.sexoService = sexoService;
    }

    @GetMapping // Notación para manejar solicitudes GET
    public List<Sexo> getSexo() { return this.sexoService.getSexo(); }

}
