package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.Services.ProgramaAcademicoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class EscuelaProgramaControladorSaes {
    private final ProgramaAcademicoServicio programaAcademicoServicio;

    @Autowired
    public EscuelaProgramaControladorSaes(ProgramaAcademicoServicio programaAcademicoServicio) {
        this.programaAcademicoServicio = programaAcademicoServicio;
    }

    @PostMapping("/programasAcademicos")
    public List<?> obtenerProgramasAcademicos(@RequestBody Map<String, Integer> body) {
        Integer escuela = body.get("escuela");
        System.out.println("LA ESCUELA QUE RECIBE ES "+ escuela);
        return this.programaAcademicoServicio.obtenerProgramasAcademicos(escuela);
    }

    @GetMapping("/AllprogramasAcademicos")
    public List<?> obtenerTodoProgramaAcademico() {
        return this.programaAcademicoServicio.obtenerTodosProgramasAcademicos();
    }
}
