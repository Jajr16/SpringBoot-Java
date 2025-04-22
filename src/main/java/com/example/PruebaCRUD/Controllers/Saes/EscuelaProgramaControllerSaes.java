package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.ProgramaAcademicoService;
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
    private final ProgramaAcademicoService programaAcademicoService;

    @Autowired
    public EscuelaProgramaControllerSaes(ProgramaAcademicoService programaAcademicoService) {
        this.programaAcademicoService = programaAcademicoService;
    }

    @PostMapping("/programasAcademicos")
    public List<?> getProgramasAcademicos(@RequestBody Map<String, Integer> body) {
        Integer escuela = body.get("escuela");
        System.out.println("LA ESCUELA QUE RECIBE ES "+ escuela);
        return this.programaAcademicoService.getProgramasAcademicos(escuela);
    }

    @GetMapping("/AllprogramasAcademicos")
    public List<?> getAllProgramasAcademicos() {
        return this.programaAcademicoService.getAllProgramasAcademicos();
    }
}
