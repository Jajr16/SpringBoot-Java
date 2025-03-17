package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.Repositories.DetalleAlumnosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleAlumnosService {

    private final DetalleAlumnosRepository detalleAlumnosRepository;

    @Autowired
    public DetalleAlumnosService(DetalleAlumnosRepository detalleAlumnosRepository) {
        this.detalleAlumnosRepository = detalleAlumnosRepository;
    }

    public List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(String boleta) {
        return detalleAlumnosRepository.findDetalleAlumnoporboleta(boleta);
    }
}
