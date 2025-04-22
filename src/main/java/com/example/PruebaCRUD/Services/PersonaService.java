package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class PersonaService {

    HashMap<String, Object> datos = new HashMap<>();

    private final PersonaRepository personaRepository;
    private final SexoRepository sexoRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;

    @Autowired // Notación que permite inyectar dependencias
    public PersonaService(PersonaRepository personaRepository, SexoRepository sexoRepository,
                          UnidadAcademicaRepository unidadAcademicaRepository) {
        this.personaRepository = personaRepository;
        this.sexoRepository = sexoRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

     // =================== PERSONA ===================
    // Esto traerá un listado de todas las personas registradas en la BD
    public List<PersonaDTO> getPersona() {
        return personaRepository.findAllAsDTO();
    }

    /**
    * Función para registrar una nueva persona
    * */
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newPersona(Persona persona) {
        datos = new HashMap<>();

        if (persona.getCURP() == null || persona.getCURP().isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El CURP no puede estar vacío.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se comprueba que no haya un registro existente con los datos recibidos
        Optional<Persona> res = personaRepository.findPersonaByCURP(persona.getCURP());

        if (res.isPresent()) {
            datos.put("Error", true);
            datos.put("message", "Esa persona ya está registrada.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se comprueba que exista un registro del sexo de la persona con los datos recibidos
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

        // Se comprueba que la Unidad Académica recibida exista
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
        datos.put("message", "La persona fue registrada exitosamente.");

        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    /**
    * Función para eliminar una persona
    * */
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

    public List<DataPersonaDTO> NombreUsuario(String usuario){

        List<Object[]> results = personaRepository.callobtenerpersona(usuario);

        List<DataPersonaDTO> responseList = new ArrayList<>();

        for (Object[] result : results) {
            String nombre = (String) result[0];
            String apellidoP = (String) result[1];
            String apellidoM = (String) result[2];

            responseList.add(new DataPersonaDTO(nombre, apellidoP, apellidoM));

        }

        return responseList;

    }

    static Persona Persona(String curp, String nombre, String apellidoP, String apellidoM, String sexo2, UnidadAcademica unidadAcademica) {
        Persona persona = new Persona();
        persona.setCURP(curp);
        persona.setNombre(nombre);
        persona.setApellido_P(apellidoP);
        persona.setApellido_M(apellidoM);

        Sexo sexo = new Sexo();
        sexo.setNombre(sexo2);
        persona.setSexo(sexo);

        persona.setUnidadAcademica(unidadAcademica);

        return persona;
    }

}
