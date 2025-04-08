package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.ListInsAlumnProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsETSProjectionSaes;
import com.example.PruebaCRUD.Services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class AlumnoControllerSaes {
    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoControllerSaes(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping("/studentperschool/{usuario}")
    public ResponseEntity<List<ListInsAlumnProjectionSaes>> getAlumnos(@PathVariable("usuario") String usuario) {
        List<ListInsAlumnProjectionSaes> response = this.alumnoService.getAlumnos(usuario);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (ListInsAlumnProjectionSaes alumno : response) {
                System.out.println("Alumno: " + alumno.getNombre() + " " + alumno.getBoleta());
            }
        }

        return ResponseEntity.ok(response);
    }
}
