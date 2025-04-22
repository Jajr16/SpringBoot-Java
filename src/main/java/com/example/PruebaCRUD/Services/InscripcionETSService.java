package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NewInscripcionRequestSaes;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class InscripcionETSService {
    HashMap<String, Object> datos = new HashMap<>();

    private final ETSRepository etsRepository;
    private final InscripcionETSRepository inscripcionETSRepository;
    private final AlumnoRepository alumnoRepository;

    @Autowired
    public InscripcionETSService(ETSRepository etsRepository, InscripcionETSRepository inscripcionETSRepository,
                                 AlumnoRepository alumnoRepository) {
        this.etsRepository = etsRepository;
        this.inscripcionETSRepository = inscripcionETSRepository;
        this.alumnoRepository = alumnoRepository;
    }

    public List<ListInsETSProjectionSaes> getMaterias(String usuario){
        return this.etsRepository.findETSL(usuario);
    }

    // Función para inscribir un alumno a un ets
    @Transactional
    public ResponseEntity<Object> newInscripcion(NewInscripcionRequestSaes newInscripcionRequestSaes) {
        datos = new HashMap<>();
        System.out.println("AQUI SE SUPONE QUE ES" + newInscripcionRequestSaes);

        if (Stream.of(newInscripcionRequestSaes.getEts(), newInscripcionRequestSaes.getPeriodo(),
                        newInscripcionRequestSaes.getBoleta(), newInscripcionRequestSaes.getTurno())
                .anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Optional<ETS> ets = this.etsRepository.findByPTUA(
                newInscripcionRequestSaes.getPeriodo(),
                newInscripcionRequestSaes.getTurno(),
                newInscripcionRequestSaes.getEts(),
                newInscripcionRequestSaes.getUser()
        );

        System.out.println("EL ETS ES " + ets);
        if (ets.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "No existe ese ETS");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Optional<Alumno> alumno = this.alumnoRepository.findByBoleta(newInscripcionRequestSaes.getBoleta());

        if (alumno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "No existe ese Alumno");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Crear clave primaria compuesta
        InscripcionETSPK inspk = new InscripcionETSPK();
        inspk.setIdETS(ets.get().getIdETS());
        inspk.setBoleta(newInscripcionRequestSaes.getBoleta());

        // Validar si ya existe la inscripción
        boolean exists = inscripcionETSRepository.existsById(inspk);

        if (exists) {
            datos.put("Error", true);
            datos.put("message", "El alumno ya está inscrito en este ETS");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Crear objeto de inscripción
        InscripcionETS newins = new InscripcionETS();
        newins.setId(inspk);
        newins.setEts(ets.get());
        newins.setAlumno(alumno.get());

        inscripcionETSRepository.save(newins);

        datos.put("message", "Alumno inscrito con éxito.");
        return new ResponseEntity<>(datos, HttpStatus.CREATED);
    }

    public List<InscripcionesDTOSaes> getInscripciones(String usuario) {
        return this.inscripcionETSRepository.getInscripciones(usuario);
    }

    public List<ListAlumnosDTO> ListarAlumnos(Integer idetss) {
        List<Object[]> results = inscripcionETSRepository.callObtenerAsistenciaDetalles(idetss);

        List<ListAlumnosDTO> responseList = new ArrayList<>();

        System.out.println("resultado" + results);

        // Iterar sobre cada resultado y mapearlo a un DTO
        for (Object[] result : results) {
            Integer idets = (Integer) result[0];

            String boleta = (String) result[1];
            String curp = (String) result[2];
            String nombreA = (String) result[3];
            String apellidoP = (String) result[4];
            String apellidoM = (String) result[5];

            String sexo = (String) result[6];

            String correo = (String) result[7];
            String carrera = (String) result[8];

            Integer aceptado = (Integer) result[9];

            // Crear un DTO y agregarlo a la lista de respuestas
            responseList.add(new ListAlumnosDTO(idets, boleta, curp, nombreA, apellidoP, apellidoM,sexo,correo,carrera,aceptado));
        }

        return responseList;
    }

    public List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(String boleta) {
        System.out.println(inscripcionETSRepository.findDetalleAlumnoporboleta(boleta));
        return inscripcionETSRepository.findDetalleAlumnoporboleta(boleta);
    }

}
