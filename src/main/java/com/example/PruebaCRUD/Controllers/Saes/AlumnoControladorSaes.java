package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListaAlumnosInscritosProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevoVideoAlumnoDTOSaes;
import com.example.PruebaCRUD.Services.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class AlumnoControladorSaes {
    private final AlumnoServicio alumnoServicio;

    @Autowired
    public AlumnoControladorSaes(AlumnoServicio alumnoServicio) {
        this.alumnoServicio = alumnoServicio;
    }

    @GetMapping("/studentperschool/{usuario}")
    public ResponseEntity<List<ListaAlumnosInscritosProjectionSaes>> obtenerAlumnosInscritos(@PathVariable("usuario") String usuario) {
        List<ListaAlumnosInscritosProjectionSaes> response = this.alumnoServicio.obtenerAlumnos(usuario);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (ListaAlumnosInscritosProjectionSaes alumno : response) {
                System.out.println("Alumno: " + alumno.getNombre() + " " + alumno.getBoleta());
            }
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/alumnos") // Notación para manejar solicitudes GET
    public ResponseEntity<List<AlumnoDTOSaes>> obtenerAlumnos(){
        List<AlumnoDTOSaes> response = this.alumnoServicio.obtenerAlumnos();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nvAlumno")
    public ResponseEntity<Object> nuevoVideoAlumno(@ModelAttribute NuevoVideoAlumnoDTOSaes nuevoVideoAlumnoDTOSaes)
            throws IOException {
        return this.alumnoServicio.nuevoVideoAlumno(nuevoVideoAlumnoDTOSaes);
    }
}
