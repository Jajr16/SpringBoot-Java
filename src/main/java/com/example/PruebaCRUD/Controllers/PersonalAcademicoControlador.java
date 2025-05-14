package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.Services.DocenteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class PersonalAcademicoControlador {

    private final DocenteServicio docenteServicio;

    @Autowired
    public PersonalAcademicoControlador(DocenteServicio docenteServicio) {
        this.docenteServicio = docenteServicio;
    }

    /**
     * Obtiene todos los docentes
     * @return Lista de docentes en formato DTO
     */
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosDocentes() {
        List<DocenteDTO> docentes = docenteServicio.obtenerDocentes();
        return ResponseEntity.ok(docentes);
    }
}
