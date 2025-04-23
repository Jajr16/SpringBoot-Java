package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.Services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class PersonalAcademicoController {

    private final DocenteService docenteService;

    @Autowired
    public PersonalAcademicoController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    /**
     * Obtiene todos los docentes
     * @return Lista de docentes en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosDocentes() {
        List<DocenteDTO> docentes = docenteService.getDocentes();
        return ResponseEntity.ok(docentes);
    }
}
