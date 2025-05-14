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
public class InscripcionesControllerSaes {
    private final InscripcionETSServicio inscripcionETSService;

    @Autowired
    public InscripcionesControllerSaes(InscripcionETSServicio inscripcionETSService) {
        this.inscripcionETSService = inscripcionETSService;
    }

    @GetMapping("/etsperschool/{usuario}")
    public ResponseEntity<List<ListaInscripcionETSProjectionSaes>> getMaterias(@PathVariable("usuario") String usuario) {
        List<ListaInscripcionETSProjectionSaes> response = this.inscripcionETSService.getMaterias(usuario);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (ListaInscripcionETSProjectionSaes materia : response) {
                System.out.println("Materia: " + materia.getNombre());
            }
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inscripciones/{usuario}")
    public ResponseEntity<List<?>> getInscripciones(@PathVariable("usuario") String usuario) {
        List<InscripcionesDTOSaes> response = this.inscripcionETSService.getInscripciones(usuario);
        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/nIns")
    public ResponseEntity<Object> newInscripcion(@RequestBody NuevaPeticionInscripcionSaes nuevaPeticionInscripcionSaes) {
        return this.inscripcionETSService.newInscripcion(nuevaPeticionInscripcionSaes);
    }
}
