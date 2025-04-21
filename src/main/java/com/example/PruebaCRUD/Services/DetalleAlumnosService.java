package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleAlumnosService {

    private final InscripcionETSRepository inscripcionETSRepository;

    @Autowired
    public DetalleAlumnosService(InscripcionETSRepository inscripcionETSRepository) {
        this.inscripcionETSRepository = inscripcionETSRepository;
    }

    public List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(String boleta) {
        System.out.println(inscripcionETSRepository.findDetalleAlumnoporboleta(boleta));
        return inscripcionETSRepository.findDetalleAlumnoporboleta(boleta);
    }
}
