package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.EscuelaProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class EscuelaProgramaControllerSaes {
    private final EscuelaProgramaService escuelaProgramaService;

    @Autowired
    public EscuelaProgramaControllerSaes(EscuelaProgramaService escuelaProgramaService) {
        this.escuelaProgramaService = escuelaProgramaService;
    }

    @PostMapping("/programasAcademicos")
    public List<?> getProgramasAcademicos(@RequestBody Map<String, Integer> body) {
        Integer escuela = body.get("escuela");
        System.out.println("BUENO AQUI ES "+ escuela);
        return this.escuelaProgramaService.getProgramasAcademicos(escuela);
    }

    @GetMapping("/AllprogramasAcademicos")
    public List<?> getAllProgramasAcademicos() {
        return this.escuelaProgramaService.getAllProgramasAcademicos();
    }
}
