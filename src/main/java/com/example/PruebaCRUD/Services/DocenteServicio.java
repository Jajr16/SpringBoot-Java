package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import com.example.PruebaCRUD.DTO.DocenteDTO;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOParaETS;
import com.example.PruebaCRUD.DTO.Saes.NuevoDocenteDTOSaes;
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
public class DocenteServicio {
    private final PersonaServicio personaServicio;
    HashMap<String, Object> datos = new HashMap<>();

    private final CargoRepositorio cargoRepositorio;
    private final PersonalAcademicoRepositorio personalAcademicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final TipoPersonalRepositorio tipoPersonalRepositorio;
    private final UnidadAcademicaRepositorio unidadAcademicaRepositorio;
    private final CargoDocenteRepositorio cargoDocenteRepositorio;
    private final AplicaRepositorio aplicaRepositorio;

    @Autowired
    public DocenteServicio(CargoRepositorio cargoRepositorio, PersonalAcademicoRepositorio personalAcademicoRepositorio,
                           UsuarioRepositorio usuarioRepositorio, TipoPersonalRepositorio tipoPersonalRepositorio,
                           UnidadAcademicaRepositorio unidadAcademicaRepositorio,
                           CargoDocenteRepositorio cargoDocenteRepositorio, PersonaServicio personaServicio, AplicaRepositorio aplicaRepositorio) {
        this.cargoRepositorio = cargoRepositorio;
        this.personalAcademicoRepositorio = personalAcademicoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.tipoPersonalRepositorio = tipoPersonalRepositorio;
        this.unidadAcademicaRepositorio = unidadAcademicaRepositorio;
        this.cargoDocenteRepositorio = cargoDocenteRepositorio;
        this.personaServicio = personaServicio;
        this.aplicaRepositorio = aplicaRepositorio;
    }

    public List<?> obtenerCargos() {
        return this.cargoRepositorio.findAllBy();
    }

    //    Función para traer a todos los docentes
    public List<DocenteDTO> obtenerDocentes() {
        return personalAcademicoRepositorio.findDocentes();
    }

    //    Función para traer a todos los docentes
    public List<DocentesDTOSaes> obtenerDocentesSaes() {
        return personalAcademicoRepositorio.findDocentesSaes();
    }

    //    Función para traer a todos los docentes
    public List<DocentesDTOParaETS> obtenerDocentesParaETS() {
        return personalAcademicoRepositorio.findDocentesToSaes();
    }

    /**
     * Función para crear un nuevo Docente
     */
    @Transactional // Notación que indica que si algo falla, hace un rollback a todas las transacciones ya hechas
    public ResponseEntity<Object> nuevoDocente(NuevoDocenteDTOSaes docente) {
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
        Integer escuela = usuarioRepositorio.findEscuela(docente.getUser());

        if (escuela == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela.");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        UnidadAcademica unidadAcademica = unidadAcademicaRepositorio.getReferenceById(escuela);

        if (unidadAcademica == null) {
            datos.put("Error", true);
            datos.put("message", "Hubo un error al asignar la escuela");

            return new ResponseEntity<>(
                    datos,
                    HttpStatus.CONFLICT
            );
        }

        Persona persona;
        persona = PersonaServicio.Persona(docente.getCurp(), docente.getNombre(), docente.getApellido_p(),
                docente.getApellido_m(), docente.getSexo(), unidadAcademica);

        ResponseEntity<Object> respuestaPersona = personaServicio.nuevaPersona(persona);
        if (respuestaPersona.getStatusCode() != HttpStatus.CREATED) {
            return respuestaPersona;
        }

        // Se busca el tipo de personal académico que era (en este caso siempre será docente) para guardarlo
        // posteriormente
        Optional<TipoPersonal> tipo = tipoPersonalRepositorio.findByCargo("Docente");

        // Se guardan los datos en una clase PersonalAcademico
        PersonalAcademico nuevoPersonalAcademico = new PersonalAcademico();
        nuevoPersonalAcademico.setRFC(docente.getRfc());
        nuevoPersonalAcademico.setCURP(persona);
        nuevoPersonalAcademico.setCorreoI(docente.getCorreo());
        nuevoPersonalAcademico.setTipoPA(tipo.get());
        // Se guarda el registro en la BD
        personalAcademicoRepositorio.save(nuevoPersonalAcademico);

        // Se buscan los datos de los cargos de los docentes para guardarlos posteriormente
        Optional<Cargo> cargo = cargoRepositorio.findByCargo(docente.getCargo());
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
        cargoDocente.setRFC(nuevoPersonalAcademico);
        cargoDocente.setIdCargo(cargo.get());

        cargoDocenteRepositorio.save(cargoDocente);

        // Se devuelve la respuesta de éxito al cliente
        datos.put("message", "Docente registrado con éxito.");
        return new ResponseEntity<>(
                datos,
                HttpStatus.CREATED
        );
    }

        public String obtenerRfcDocente(int idets) {
        return aplicaRepositorio.callObtenerDocenteRfc(idets);
    }
}
