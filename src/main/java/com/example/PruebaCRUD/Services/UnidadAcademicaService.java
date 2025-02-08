package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.UnidadAcademicaRepository;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class UnidadAcademicaService {
    private final UnidadAcademicaRepository unidadAcademicaRepository;

    @Autowired // Notación que permite inyectar dependencias
    public UnidadAcademicaService(UnidadAcademicaRepository unidadAcademicaRepository) {
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    public List<UnidadAcademica> getUA() {
        return this.unidadAcademicaRepository.findAll();
    }
}
