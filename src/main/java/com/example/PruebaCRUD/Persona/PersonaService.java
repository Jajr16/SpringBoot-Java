package com.example.PruebaCRUD.Persona;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.BD.Repositories.SexoRepository;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import com.example.PruebaCRUD.BD.Repositories.UnidadAcademicaRepository;
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
    private final SexoRepository sexoRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;

    // Con esta inyección podremos hacer el CRUD de forma directa
    @Autowired
    public PersonaService(PersonaRepository personaRepository, SexoRepository sexoRepository,
                          UnidadAcademicaRepository unidadAcademicaRepository) {
        this.personaRepository = personaRepository;
        this.sexoRepository = sexoRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    // Esto traerá un listado de todas las personas registradas en la BD
    public List<Persona> getPersona() {
        return this.personaRepository.findAll();
    }

    /**
    * Función para registrar una nueva persona
    * */
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

        Optional<Sexo> sexOpt = sexoRepository.findByNombre(persona.getSexo().getNombre());
        if (sexOpt.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El sexo proporcionado no es válido: " +
                    persona.getSexo().getNombre());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        persona.setSexo(sexOpt.get());

        Optional<UnidadAcademica> uaOP =
                unidadAcademicaRepository.findByNombre(persona.getUnidadAcademica().getNombre());
        if (uaOP.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "La unidad académica ingresada no existe: " +
                    persona.getUnidadAcademica().getNombre());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }
        persona.setUnidadAcademica(uaOP.get());


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
