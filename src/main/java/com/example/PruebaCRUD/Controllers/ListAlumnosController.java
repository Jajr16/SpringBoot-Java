package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Services.ListETSService2;
import com.example.PruebaCRUD.Services.ListaAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("alumno/inscritosETS")
public class ListAlumnosController {

    private final ListaAlumnoService listaAlumnoService;

    @Autowired
    public ListAlumnosController(ListaAlumnoService listaAlumnoService) {
        this.listaAlumnoService = listaAlumnoService;
    }

    @GetMapping("/{ETSid}") // Notación para manejar solicitudes GET
    public ResponseEntity<List<ListAlumnosDTO>> inscripList(@PathVariable("ETSid") String idets) {
        System.out.println("dato" + idets);
        List<ListAlumnosDTO> response = listaAlumnoService.ListarAlumnos(Integer.valueOf(idets));
        System.out.println("regreso" + response);

        return ResponseEntity.ok(response);
    }


}
