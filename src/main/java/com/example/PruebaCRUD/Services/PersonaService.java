package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.DTO.*;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOToETS;
import com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes;
import com.example.PruebaCRUD.Repositories.*;
import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

 /**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class PersonaService {
    HashMap<String, Object> datos = new HashMap<>();

    private final PersonaRepository personaRepository;
    private final SexoRepository sexoRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;
    private final PersonalSeguridadRepository personalSeguridadRepository;

    // Con esta inyección podremos hacer el CRUD de forma directa
    @Autowired // Notación que permite inyectar dependencias
    public PersonaService(PersonaRepository personaRepository, SexoRepository sexoRepository,
                          UnidadAcademicaRepository unidadAcademicaRepository,
                          AlumnoRepository alumnoRepository, DocenteRepository docenteRepository, PersonalSeguridadRepository personalSeguridadRepository) {
        this.personaRepository = personaRepository;
        this.sexoRepository = sexoRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
        this.alumnoRepository = alumnoRepository;
        this.docenteRepository = docenteRepository;
        this.personalSeguridadRepository = personalSeguridadRepository;
    }

    // Función para traer a todos los alumnos
    public List<AlumnoDTOSaes> getAlumnos() {
        return alumnoRepository.findAllAsDTO();
    }

//    Función para traer a todos los docentes
    public List<DocentesDTOSaes> getDocentes() {
        return docenteRepository.findDocentes();
    }

//    Función para traer a todos los docentes
    public List<DocentesDTOToETS> getDocentesToETS() {
        return docenteRepository.findDocentesToSaes();
    }

//    Función para traer a todo el personal de seguridad
    public List<PersonalSeguridadDTOSaes> getPS() {
        return personalSeguridadRepository.findPersonalSeguridad();
    }

    // Esto traerá un listado de todas las personas registradas en la BD
    public List<PersonaDTO> getPersona() {
        return personaRepository.findAllAsDTO();
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
}
