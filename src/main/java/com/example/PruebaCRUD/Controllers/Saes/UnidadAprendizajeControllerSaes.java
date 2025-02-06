package com.example.PruebaCRUD.Controllers.Saes;

import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import com.example.PruebaCRUD.Services.UnidadAprendizajeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/saes")
public class UnidadAprendizajeControllerSaes {

    private final UnidadAprendizajeService unidadAprendizajeService;

    public UnidadAprendizajeControllerSaes(UnidadAprendizajeService unidadAprendizajeService) {
        this.unidadAprendizajeService = unidadAprendizajeService;
    }

    @GetMapping("/UAprenToETS")
    public ResponseEntity<List<UnidadAprendizajeProjectionSaes>> getUAprenToETS() {
        List<UnidadAprendizajeProjectionSaes> response = this.unidadAprendizajeService.getUApren();

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
