package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NewInscripcionRequestSaes;
import com.example.PruebaCRUD.Services.InscripcionETSService;
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
    private final InscripcionETSService inscripcionETSService;

    @Autowired
    public InscripcionesControllerSaes(InscripcionETSService inscripcionETSService) {
        this.inscripcionETSService = inscripcionETSService;
    }

    @GetMapping("/etsperschool/{usuario}")
    public ResponseEntity<List<ListInsETSProjectionSaes>> getMaterias(@PathVariable("usuario") String usuario) {
        List<ListInsETSProjectionSaes> response = this.inscripcionETSService.getMaterias(usuario);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (ListInsETSProjectionSaes materia : response) {
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
    public ResponseEntity<Object> newInscripcion(@RequestBody NewInscripcionRequestSaes newInscripcionRequestSaes) {
        return this.inscripcionETSService.newInscripcion(newInscripcionRequestSaes);
    }
}
