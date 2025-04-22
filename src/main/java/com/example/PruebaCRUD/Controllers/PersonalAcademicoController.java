package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.Services.PersonalAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class PersonalAcademicoController {

    private final PersonalAcademicoService personalAcademicoService;

    @Autowired
    public PersonalAcademicoController(PersonalAcademicoService personalAcademicoService) {
        this.personalAcademicoService = personalAcademicoService;
    }

    /**
     * Obtiene todos los docentes
     * @return Lista de docentes en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosDocentes() {
        List<DocenteDTO> docentes = personalAcademicoService.obtenerTodosLosDocentes();
        return ResponseEntity.ok(docentes);
    }
}
