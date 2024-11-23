package com.example.PruebaCRUD.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    HashMap<String, Object> datos = new HashMap<>();

    private final PersonaRepository personaRepository;

    // Con esta inyección podremos hacer el CRUD de forma directa
    @Autowired
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Esto traerá un listado de todas las personas registradas en la BD
    public List<Persona> getPersona() {
        return this.personaRepository.findAll();
    }

    public ResponseEntity<Object> newPersona(Persona persona) {
        datos = new HashMap<>();

        if (persona.getCURP() == null || persona.getCURP().isEmpty()) {
            datos = new HashMap<>();
            datos.put("Error", true);
            datos.put("message", "El CURP no puede estar vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<Persona> res = personaRepository.findPersonaByCURP(persona.getCURP());

        if (res.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Esa persona ya está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        personaRepository.save(persona);
        datos.put("data", persona);
        datos.put("message", "La persona fue registrado exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<Object> deletePersona(String curp) {
        datos = new HashMap<>();

        boolean exists = this.personaRepository.existsById(curp);

        if (!exists) {
            datos.put("Error", true);
            datos.put("message", "Esta persona no está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        personaRepository.deleteById(curp);
        datos.put("message", "Persona eliminada.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.ACCEPTED
        );

    }
}
