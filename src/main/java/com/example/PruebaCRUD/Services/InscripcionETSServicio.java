package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.DTO.ListaAlumnosDTO;
import com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListaInscripcionETSProjectionSaes;
import com.example.PruebaCRUD.DTO.Saes.NuevaPeticionInscripcionSaes;
import com.example.PruebaCRUD.Repositories.AlumnoRepositorio;
import com.example.PruebaCRUD.Repositories.ETSRepositorio;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepositorio;
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
public class InscripcionETSServicio {
    HashMap<String, Object> datos = new HashMap<>();

    private final ETSRepositorio etsRepositorio;
    private final InscripcionETSRepositorio inscripcionETSRepository;
    private final AlumnoRepositorio alumnoRepository;

    @Autowired
    public InscripcionETSServicio(ETSRepositorio etsRepositorio, InscripcionETSRepositorio inscripcionETSRepository,
                                  AlumnoRepositorio alumnoRepository) {
        this.etsRepositorio = etsRepositorio;
        this.inscripcionETSRepository = inscripcionETSRepository;
        this.alumnoRepository = alumnoRepository;
    }

    public List<ListaInscripcionETSProjectionSaes> getMaterias(String usuario){
        return this.etsRepositorio.findETSL(usuario);
    }

    // Función para inscribir un alumno a un ets
    @Transactional
    public ResponseEntity<Object> newInscripcion(NuevaPeticionInscripcionSaes nuevaPeticionInscripcionSaes) {
        datos = new HashMap<>();
        System.out.println("AQUI SE SUPONE QUE ES" + nuevaPeticionInscripcionSaes);

        if (Stream.of(nuevaPeticionInscripcionSaes.getEts(), nuevaPeticionInscripcionSaes.getPeriodo(),
                        nuevaPeticionInscripcionSaes.getBoleta(), nuevaPeticionInscripcionSaes.getTurno())
                .anyMatch(val -> val == null || val.isEmpty())) {
            datos.put("Error", true);
            datos.put("message", "Debes de llenar todos los campos");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Optional<ETS> ets = this.etsRepositorio.findByPTUA(
                nuevaPeticionInscripcionSaes.getPeriodo(),
                nuevaPeticionInscripcionSaes.getTurno(),
                nuevaPeticionInscripcionSaes.getEts(),
                nuevaPeticionInscripcionSaes.getUser()
        );

        System.out.println("EL ETS ES " + ets);
        if (ets.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "No existe ese ETS");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        Optional<Alumno> alumno = this.alumnoRepository.findByBoleta(nuevaPeticionInscripcionSaes.getBoleta());

        if (alumno.isEmpty()) {
            datos.put("Error", true);
            datos.put("message", "No existe ese Alumno");

            return new ResponseEntity<>(datos, HttpStatus.CONFLICT);
        }

        // Crear clave primaria compuesta
        InscripcionETSPK inspk = new InscripcionETSPK();
        inspk.setIdETS(ets.get().getIdETS());
        inspk.setBoleta(nuevaPeticionInscripcionSaes.getBoleta());

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

    public List<ListaAlumnosDTO> ListarAlumnos(Integer idetss) {
        List<Object[]> results = inscripcionETSRepository.callObtenerAsistenciaDetalles(idetss);

        List<ListaAlumnosDTO> responseList = new ArrayList<>();

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
            responseList.add(new ListaAlumnosDTO(idets, boleta, curp, nombreA, apellidoP, apellidoM,sexo,correo,carrera,aceptado));
        }

        return responseList;
    }

    public List<DetalleAlumnosDTO> encontrarDetalleAlumnoporboleta(String boleta) {
        System.out.println(inscripcionETSRepository.findDetalleAlumnoporboleta(boleta));
        return inscripcionETSRepository.findDetalleAlumnoporboleta(boleta);
    }

}
