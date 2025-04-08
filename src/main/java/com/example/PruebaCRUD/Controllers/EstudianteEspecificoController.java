package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.EstudianteEspecificoDTO;
import com.example.PruebaCRUD.Services.EstudianteEspecificoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/InfoA")
public class EstudianteEspecificoController {

    private final EstudianteEspecificoService estudianteEspecificoService;

    @Autowired
    public EstudianteEspecificoController(EstudianteEspecificoService estudianteEspecificoService) {
        this.estudianteEspecificoService = estudianteEspecificoService;
    }

    @GetMapping("/{boleta}")
    public EstudianteEspecificoDTO obtenerEstudiante(@PathVariable String boleta) {
        return estudianteEspecificoService.obtenerEstudiantePorBoleta(boleta);
    }
}
