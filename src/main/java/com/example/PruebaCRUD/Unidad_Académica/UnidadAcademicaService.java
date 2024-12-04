package com.example.PruebaCRUD.Unidad_Académica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadAcademicaService {
    private final UnidadAcademicaRepository unidadAcademicaRepository;

    @Autowired
    public UnidadAcademicaService(UnidadAcademicaRepository unidadAcademicaRepository) {
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    public List<UnidadAcademica> getUA() {
        return this.unidadAcademicaRepository.findAll();
    }
}
