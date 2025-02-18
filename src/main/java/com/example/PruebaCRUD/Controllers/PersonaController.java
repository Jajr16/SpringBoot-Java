package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.DTO.PersonaDTO;
import com.example.PruebaCRUD.Services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/Persona")// Indicar la ruta de a donde quieres que vaya // Esto marca la función disponible para consulta
public class PersonaController {
    private final PersonaService personaService;

    @Autowired // Notación para inyectar esta clase en el constructor
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping //Aquí se ocupa esto para que entre al método de abajo
    public List<PersonaDTO> getPersona() {
        return this.personaService.getPersona();
    }

    @PostMapping // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarPersona(@RequestBody Persona persona) {
        return this.personaService.newPersona(persona);
    }

    @DeleteMapping(path = "{persId}") // Notación para manejar solicitudes POST de eliminación
    public ResponseEntity<Object> eliminarPersona(@PathVariable("persId") String curp) {
        return this.personaService.deletePersona(curp);
    }

//    @PutMapping
//    public ResponseEntity<Object> actualizarPersona(@RequestBody Persona persona) {
//        return this.personaService.newPersona(persona);
//    }
}
