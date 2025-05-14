package com.example.PruebaCRUD.Controllers;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.DTO.DatosPersonaDTO;
import com.example.PruebaCRUD.DTO.PersonaDTO;
import com.example.PruebaCRUD.Services.PersonaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Clase API que tendrá los endpoints
 */
@RestController // Notación que defina el controlador REST (Solicitudes HTTP)
@RequestMapping(path = "/Persona")// Indicar la ruta de a donde quieres que vaya // Esto marca la función disponible para consulta
public class PersonaControlador {
    private final PersonaServicio personaServicio;

    @Autowired // Notación para inyectar esta clase en el constructor
    public PersonaControlador(PersonaServicio personaServicio) {
        this.personaServicio = personaServicio;
    }

    @GetMapping //Aquí se ocupa esto para que entre al método de abajo
    public List<PersonaDTO> obtenerPersona() {
        return this.personaServicio.obtenerPersona();
    }

    @PostMapping // Notación para manejar solicitudes POST
    public ResponseEntity<Object> registrarPersona(@RequestBody Persona persona) {
        return this.personaServicio.nuevaPersona(persona);
    }

    @DeleteMapping(path = "{persId}") // Notación para manejar solicitudes POST de eliminación
    public ResponseEntity<Object> eliminarPersona(@PathVariable("persId") String curp) {
        return this.personaServicio.eliminarPersona(curp);
    }

    @GetMapping("/datos/{usuario}")
    public ResponseEntity<List<DatosPersonaDTO>> nombre(@PathVariable("usuario") String usuario) {

        List<DatosPersonaDTO> respuesta = personaServicio.NombreUsuario(usuario);

        return ResponseEntity.ok(respuesta);
    }
}
