package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.AlumnoDTO;
import com.example.PruebaCRUD.Services.AlumnoService;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/inscritosETS/{fecha}/{periodo}")
    public List<AlumnoDTO> findAlumnosInscritosETS(
            @PathVariable("fecha") Date fecha,
            @PathVariable("periodo") Integer periodo
    ) {
        return alumnoService.findAlumnosInscritosETS(fecha, periodo);
    }
}
