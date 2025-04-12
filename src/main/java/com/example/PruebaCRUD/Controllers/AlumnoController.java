package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.AlumnoDTO;
import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.Services.AlumnoService;
import com.example.PruebaCRUD.Services.DetalleAlumnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final DetalleAlumnosService detalleAlumnosService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService, DetalleAlumnosService detalleAlumnosService) {
        this.alumnoService = alumnoService;
        this.detalleAlumnosService = detalleAlumnosService;
    }

    @GetMapping("/inscritosETS")
    public List<AlumnoDTO>findAlumnosInscritosETS() {
        return alumnoService.findAlumnosInscritosETS();

    }

    @GetMapping("/detalle/{boleta}")
    public List<DetalleAlumnosDTO>findDetalleAlumnoporboleta(@PathVariable String boleta){
        return detalleAlumnosService.findDetalleAlumnoporboleta(boleta);
    }
}

