package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.AlumnoDTO;
import com.example.PruebaCRUD.DTO.ListAlumnosDTO;
import com.example.PruebaCRUD.DTO.Saes.ListInsAlumnProjectionSaes;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InscripcionETSRepository inscripcionETSRepository;
    private final periodoETSRepository periodRepo;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository, InscripcionETSRepository inscripcionETSRepository, periodoETSRepository periodRepo) {
        this.alumnoRepository = alumnoRepository;
        this.inscripcionETSRepository = inscripcionETSRepository;
        this.periodRepo = periodRepo;
    }

    public List<AlumnoDTO>findAlumnosInscritosETS() {

        Integer año = LocalDate.now().getYear();
        Integer mes = LocalDate.now().getMonthValue();

        //Se inicializa la variable que servirá para armar el periodo en el estamos actualmente
        String periodo = "";

        // Obtiene los últimos dos dígitos del año actual
        String año_abreviado = año.toString().substring(2);

        // Se arma el periodo
        if (mes >= 8 || mes <= 1) {
            periodo = año_abreviado.concat("/1");
        } else {
            periodo = año_abreviado.concat("/2");
        }

        List<String> fechasPeriodos = periodRepo.findFechasByPeriodo(periodo);

        if (fechasPeriodos == null) { // Si la fecha obtenida es null
            List<AlumnoDTO> lista = new ArrayList<>();  // Usamos ArrayList como implementación de List
            return lista;  // Retornamos la lista que contiene el error
        }

        String fechasString = fechasPeriodos.get(0);

        String[] fechasSeparadas = fechasString.split(",");

        System.out.println("LAS FECHAS DEL PERIODO " + periodo + " SON " + fechasPeriodos);

        // Fecha actual
        Date fechaActual = java.sql.Date.valueOf(LocalDate.now());

        Date fechaInicio = java.sql.Date.valueOf(LocalDate.parse(fechasSeparadas[0]));
        Date fechaFin = java.sql.Date.valueOf(LocalDate.parse(fechasSeparadas[1]));

        List<AlumnoDTO> results = inscripcionETSRepository.findAlumnosInscritosETS(fechaActual, fechaInicio, fechaFin,
                periodo);

        System.out.println("LOS ALUMNOS QUE PRESENTAN ETS HOY SON: " + results);
        return results;
    }

    public List<ListInsAlumnProjectionSaes> getAlumnos(String usuario) {
        return this.alumnoRepository.findAlumnosSaes(usuario);
    }
}
