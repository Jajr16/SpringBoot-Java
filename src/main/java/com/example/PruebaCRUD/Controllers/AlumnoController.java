package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.Services.AlumnoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/inscritosETS/{ETSid}")
    public List<String> obtenerAlumnosInscritos(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam String periodo) {
        System.out.println("LA PERRA FECHA ES " + fecha);
        return alumnoService.obtenerAlumnosInscritos(fecha, periodo);
    }
}
