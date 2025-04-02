package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Services.IngresoInstalacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alumno/")
public class IngresoInstalacionController {

    @Autowired
    private IngresoInstalacionService ingresoInstalacionService;

    @GetMapping("ingreso/{boleta}")
    public ResponseEntity<List<IngresoInstalacionDTO>> getAlumnosInscritosETS(@PathVariable String boleta) {
        List<IngresoInstalacionDTO> alumnos = ingresoInstalacionService.getAlumnosInscritosETS(boleta);
        if (alumnos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
    }
}
