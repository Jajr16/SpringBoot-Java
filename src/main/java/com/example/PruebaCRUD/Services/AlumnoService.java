package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.AlumnoDTO;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlumnoService {
    private final InscripcionETSRepository inscripcionETSRepository;

    @Autowired
    public AlumnoService(InscripcionETSRepository inscripcionETSRepository) {
        this.inscripcionETSRepository = inscripcionETSRepository;
    }

    public List<AlumnoDTO> findAlumnosInscritosETS(Date fecha, Integer periodo) {
        List<Object[]> results = inscripcionETSRepository.findAlumnosInscritosETS(fecha, periodo);
        List<AlumnoDTO> responseList = new ArrayList<>();

        // Iterar sobre cada resultado y mapearlo a un DTO
        for (Object[] result : results) {
            String boleta = (String) result[0];
            String Nombre = (String) result[1];
            String Apellido_P = (String) result[2];
            String Apellido_M = (String) result[3];
            Date fechabd = (Date) result[4];  // La fecha que viene de la BD
            Integer periodobd = (Integer) result[5]; // El periodo desde la BD

            // Convertir fecha e idPeriodo a String
            String fechaStr = fechabd.toString();
            String periodoStr = periodobd.toString();

            // Crear un DTO con los valores convertidos y agregarlo a la lista de respuestas
            responseList.add(new AlumnoDTO(boleta, Nombre, Apellido_P, Apellido_M, fechaStr, periodoStr));
        }
        return responseList;
    }
}
