package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOToETS;
import com.example.PruebaCRUD.DTO.Saes.NewDocentesDTOSaes;
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
public class DocenteService {
    private final PersonaService personaService;
    HashMap<String, Object> datos = new HashMap<>();

    private final CargoRepository cargoRepository;
    private final PersonalAcademicoRepository personalAcademicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TipoPersonalRepository tipoPersonalRepository;
    private final UnidadAcademicaRepository unidadAcademicaRepository;
    private final CargoDocenteRepository cargoDocenteRepository;
    private final AplicaRepository aplicaRepository;

    @Autowired
    public DocenteService(CargoRepository cargoRepository, PersonalAcademicoRepository personalAcademicoRepository,
                          UsuarioRepository usuarioRepository, TipoPersonalRepository tipoPersonalRepository,
                          UnidadAcademicaRepository unidadAcademicaRepository,
                          CargoDocenteRepository cargoDocenteRepository, PersonaService personaService, AplicaRepository aplicaRepository) {
        this.cargoRepository = cargoRepository;
        this.personalAcademicoRepository = personalAcademicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tipoPersonalRepository = tipoPersonalRepository;
        this.unidadAcademicaRepository = unidadAcademicaRepository;
        this.cargoDocenteRepository = cargoDocenteRepository;
        this.personaService = personaService;
        this.aplicaRepository = aplicaRepository;
    }

    public List<?> getCargos() {
        return this.cargoRepository.findAllBy();
    }

    //    Función para traer a todos los docentes
    public List<DocentesDTOSaes> getDocentes() {
        return personalAcademicoRepository.findDocentesSaes();
    }

    //    Función para traer a todos los docentes
    public List<DocentesDTOToETS> getDocentesToETS() {
        return personalAcademicoRepository.findDocentesToSaes();
    }

    /**
     * Función para crear un nuevo Docente
     */
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> newDocente(NewDocentesDTOSaes docente) {
        datos = new HashMap<>();

        // Se comprueba que nada venga vacío, en caso de que sí, se devuelve un error
        if (Stream.of(docente.getCurp(), docente.getRfc(), docente.getNombre(), docente.getApellido_p(),
                docente.getApellido_m(), docente.getSexo(), docente.getCorreo(), docente.getCargo(), docente.getTurno(),
                docente.getUser()).anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se busca la escuela a la que pertenecerá el docente para guardarlo en la bd
        Integer escuela = usuarioRepository.findEscuela(docente.getUser());

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
        persona = PersonaService.Persona(docente.getCurp(), docente.getNombre(), docente.getApellido_p(),
                docente.getApellido_m(), docente.getSexo(), unidadAcademica);

        ResponseEntity<Object> responsePersona = personaService.newPersona(persona);
        if (responsePersona.getStatusCode() != HttpStatus.CREATED) {
            return responsePersona;
        }

        // Se busca el tipo de personal académico que era (en este caso siempre será docente) para guardarlo
        // posteriormente
        Optional<TipoPersonal> tipo = tipoPersonalRepository.findByCargo("Docente");

        // Se guardan los datos en una clase PersonalAcademico
        PersonalAcademico newPersonalAcademico = new PersonalAcademico();
        newPersonalAcademico.setRFC(docente.getRfc());
        newPersonalAcademico.setCURP(persona);
        newPersonalAcademico.setCorreoI(docente.getCorreo());
        newPersonalAcademico.setTipoPA(tipo.get());
        // Se guarda el registro en la BD
        personalAcademicoRepository.save(newPersonalAcademico);

        // Se buscan los datos de los cargos de los docentes para guardarlos posteriormente
        Optional<Cargo> cargo = cargoRepository.findByCargo(docente.getCargo());
        if (cargo.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "El cargo proporcionado no es válido: " +
                    docente.getSexo());

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        // Se establece la llave primaria que tendrá en la BD
        CargoDocentePK cdpk = new CargoDocentePK();
        cdpk.setIdCargo(cargo.get().getIdCargo());
        cdpk.setRFC(docente.getRfc());

        // Se guarda el cargo que tiene como docente
        CargoDocente cargoDocente = new CargoDocente();
        cargoDocente.setId(cdpk);
        cargoDocente.setRFC(newPersonalAcademico);
        cargoDocente.setIdCargo(cargo.get());

        cargoDocenteRepository.save(cargoDocente);

        // Se devuelve la respuesta de éxito al cliente
        datos.put("message", "Docente registrado con éxito.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

    public String obtenerRfcDocente(int idets) {
        return aplicaRepository.callObtenerDocenteRfc(idets);
    }

    /**
     * Obtiene todos los docentes con su información básica en formato DTO
     * @return Lista de DocenteDTO con RFC y nombre completo
     */
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return personalAcademicoRepository.findDocentes();
    }
}
