package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ListETSService {
    private final InscripcionETSRepository inscripcionETSRepository;

    @Autowired
    public ListETSService(InscripcionETSRepository inscripcionETSRepository) {
        this.inscripcionETSRepository = inscripcionETSRepository;
    }

    public ListETSResponseDTO inscripcionesETS(String boleta) {
        List<Object[]> results = inscripcionETSRepository.callListInscripcionesETS(boleta);

        if (results == null) {
            return new ListETSResponseDTO("Ocurrió un error inesperado.");
        }

        Object[] result = results.get(0);
        Integer idets = (int) result[0];
        String periodo = (String) result[1];
        String turno = (String) result[2];
        Date fecha = (Date) result[3];
        String materia = (String) result[4];

        String fechaString = fecha.toString();

        return new ListETSResponseDTO(idets, periodo, turno, fechaString, materia);
    }

    public Boolean confirmInscripcion(String boleta) {
        return inscripcionETSRepository.existsByBoletaInsBoleta(boleta);
    }

}
