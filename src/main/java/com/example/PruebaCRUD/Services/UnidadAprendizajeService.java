package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadAprendizajeService {
    private final UnidadAprendizajeRepository unidadAprendizajeRepository;

    @Autowired
    public UnidadAprendizajeService(UnidadAprendizajeRepository unidadAprendizajeRepository) {
        this.unidadAprendizajeRepository = unidadAprendizajeRepository;
    }

    public List<UnidadAprendizajeProjectionSaes> getUApren(){
        return this.unidadAprendizajeRepository.findAllBy();
    }
}
