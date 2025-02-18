package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.ListETSResponseDTO;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ListETSService {
    private final InscripcionETSRepository inscripcionETSRepository;

    @Autowired
    public ListETSService(InscripcionETSRepository inscripcionETSRepository) {
        this.inscripcionETSRepository = inscripcionETSRepository;
    }

    public List<ListETSResponseDTO> inscripcionesETS(String boleta) {
        List<Object[]> results = inscripcionETSRepository.callListInscripcionesETS(boleta);

//        if (results == null) {
//            return new ListETSResponseDTO("Ocurrió un error inesperado.");
//        }

        List<ListETSResponseDTO> responseList = new ArrayList<>();

        // Iterar sobre cada resultado y mapearlo a un DTO
        for (Object[] result : results) {
            Integer idets = (Integer) result[0];
            String periodo = (String) result[1];
            String turno = (String) result[2];
            Date fecha = (Date) result[3];
            String materia = (String) result[4];

            // Convertir la fecha a String (o al formato que necesites)
            String fechaString = fecha.toString();

            // Crear un DTO y agregarlo a la lista de respuestas
            responseList.add(new ListETSResponseDTO(idets, periodo, turno, fechaString, materia));
        }

        return responseList;
    }

    public Boolean confirmInscripcion(String boleta) {
        return inscripcionETSRepository.existsByBoletaInsBoleta(boleta);
    }
}
