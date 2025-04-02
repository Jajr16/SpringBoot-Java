package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.DTO.DataPersonaDTO;
import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.Services.ListaAlumnoService;
import com.example.PruebaCRUD.Services.PersonaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("persona/datos")

public class PersonaDataController {

    private final PersonaDataService personaDataService;

    @Autowired
    public PersonaDataController(PersonaDataService personaDataService) {
        this.personaDataService =  personaDataService;
    }

    @GetMapping("/{usuario}")
    public ResponseEntity<List<DataPersonaDTO>> nombre(@PathVariable("usuario") String usuario) {

        List<DataPersonaDTO> response = personaDataService.NombreUsuario(usuario);


        return ResponseEntity.ok(response);
    }

}
