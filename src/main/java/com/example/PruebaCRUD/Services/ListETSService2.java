package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.AplicaRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PruebaCRUD.DTO.ListETSResponseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ListETSService2 {

    private final AplicaRepository aplicaRepository;

    @Autowired
    public ListETSService2(AplicaRepository aplicaRepository) {
        this.aplicaRepository = aplicaRepository;
    }

    public List<ListETSResponseDTO> inscripcionesETS(String docente_rfc) {
        List<Object[]> results = aplicaRepository.callListAplica(docente_rfc);

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

    public Boolean confirmInscripcion(String docente_rfc) {
        return aplicaRepository.existsByDocenteRFCRfc(docente_rfc);
    }

}
