package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.UnidadAcademicaRepository;
import com.example.PruebaCRUD.BD.UnidadAcademica;
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
