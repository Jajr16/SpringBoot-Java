package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.DTO.Saes.NewPersonalSeguridadDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes;
import com.example.PruebaCRUD.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class PersonalSeguridadService {
    private final CargoPSRepository cargoPSRepository;
    private final PersonalSeguridadRepository personalSeguridadRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurnoRepository turnoRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final PersonaService personaService;

    HashMap<String, Object> datos = new HashMap<>();

    @Autowired
    public PersonalSeguridadService(CargoPSRepository cargoPSRepository,
                                    PersonalSeguridadRepository personalSeguridadRepository,
                                    UsuarioRepository usuarioRepository, TurnoRepository turnoRepository,
                                    UnidadAcademicaRepository unidadAcademicaRepository, PersonaService personaService) {
        this.cargoPSRepository = cargoPSRepository;
        this.personalSeguridadRepository = personalSeguridadRepository;
        this.usuarioRepository = usuarioRepository;
        this.turnoRepository = turnoRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
        this.personaService = personaService;
    }

    public List<?> getCargos() {
        return this.cargoPSRepository.findAllBy();
    };

    //    Función para traer a todo el personal de seguridad
    public List<PersonalSeguridadDTOSaes> getPS() {
        return personalSeguridadRepository.findPersonalSeguridad();
    }

    /**
     * Función para crear un nuevo Personal de seguridad
     */
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newPersonalSeguridad(NewPersonalSeguridadDTOSaes personalSeguridad) {
        datos = new HashMap<>();

        if (Stream.of(personalSeguridad.getCurp(), personalSeguridad.getNombre(), personalSeguridad.getApellido_P(),
                personalSeguridad.getApellido_M(), personalSeguridad.getSexo(), personalSeguridad.getTurno(),
                personalSeguridad.getCargoPS(), personalSeguridad.getUser()).anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Integer escuela = usuarioRepository.findEscuela(personalSeguridad.getUser());

        if (escuela == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        UnidadAcademica unidadAcademica = unidadAcademicaRepository.getReferenceById(escuela);

        if (unidadAcademica == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Persona persona;
        persona = PersonaService.Persona(personalSeguridad.getCurp(), personalSeguridad.getNombre(),
                personalSeguridad.getApellido_P(), personalSeguridad.getApellido_M(), personalSeguridad.getSexo(),
                unidadAcademica);

        ResponseEntity<Object> responsePersona = personaService.newPersona(persona);
        if (responsePersona.getStatusCode() != HttpStatus.CREATED) {
            return responsePersona;
        }

        Optional<Turno> turno = turnoRepository.findByNombre(personalSeguridad.getTurno());

        if (turno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El turno proporcionado no existe.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Optional<CargoPS> cargo = cargoPSRepository.findByNombre(personalSeguridad.getCargoPS());

        if (cargo.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El cargo proporcionado no existe.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        PersonalSeguridad nuevoPersonalSeguridad = new PersonalSeguridad();
        nuevoPersonalSeguridad.setRfc(personalSeguridad.getRfc());
        nuevoPersonalSeguridad.setCURP(persona);
        nuevoPersonalSeguridad.setTurno(turno.get());
        nuevoPersonalSeguridad.setCargo(cargo.get());

        personalSeguridadRepository.save(nuevoPersonalSeguridad);

        datos.put("message", "Personal de seguridad registrada con éxito.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }
}
