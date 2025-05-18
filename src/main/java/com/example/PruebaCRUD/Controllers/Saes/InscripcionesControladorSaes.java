package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListaInscripcionETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevaPeticionInscripcionSaes;
import com.example.PruebaCRUD.Services.InscripcionETSServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping("/saes") // Mapear la url a este método
public class InscripcionesControladorSaes {
    private final InscripcionETSServicio inscripcionETSServicio;

    @Autowired
    public InscripcionesControladorSaes(InscripcionETSServicio inscripcionETSServicio) {
        this.inscripcionETSServicio = inscripcionETSServicio;
    }

    @GetMapping("/etsperschool/{usuario}")
    public ResponseEntity<List<ListaInscripcionETSProjectionSaes>> obtenerMaterias(@PathVariable("usuario") String usuario) {
        List<ListaInscripcionETSProjectionSaes> respuesta = this.inscripcionETSServicio.obtenerMaterias(usuario);
        if (respuesta.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (ListaInscripcionETSProjectionSaes materia : respuesta) {
                System.out.println("Materia: " + materia.getNombre());
            }
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/inscripciones/{usuario}")
    public ResponseEntity<List<?>> obtenerInscripciones(@PathVariable("usuario") String usuario) {
        List<InscripcionesDTOSaes> respuesta = this.inscripcionETSServicio.obtenerInscripciones(usuario);
        System.out.println(respuesta);

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/nIns")
    public ResponseEntity<Object> nuevaInscripcion(@RequestBody NuevaPeticionInscripcionSaes nuevaPeticionInscripcionSaes) {
        return this.inscripcionETSServicio.nuevaInscripcion(nuevaPeticionInscripcionSaes);
    }
}
