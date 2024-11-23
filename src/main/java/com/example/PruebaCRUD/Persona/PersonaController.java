package com.example.PruebaCRUD.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Persona")// Indicar la ruta de a donde quieres que vaya // Esto marca la función disponible para consulta
public class PersonaController {
    private final PersonaService personaService;

    @Autowired // Notación para inyectar esta clase en el constructor
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping //Aquí se ocupa esto para que entre al método de abajo
    public List<Persona> getPersona() {
        return this.personaService.getPersona();
    }

    @PostMapping
    public ResponseEntity<Object> registrarPersona(@RequestBody Persona persona) {
        return this.personaService.newPersona(persona);
    }

    @DeleteMapping(path = "{persId}")
    public ResponseEntity<Object> eliminarPersona(@PathVariable("persId") String curp) {
        return this.personaService.deletePersona(curp);
    }

//    @PutMapping
//    public ResponseEntity<Object> actualizarPersona(@RequestBody Persona persona) {
//        return this.personaService.newPersona(persona);
//    }
}
