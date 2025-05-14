package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.UnidadAcademicaRepositorio;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class UnidadAcademicaServicio {
    private final UnidadAcademicaRepositorio unidadAcademicaRepository;

    @Autowired // Notación que permite inyectar dependencias
    public UnidadAcademicaServicio(UnidadAcademicaRepositorio unidadAcademicaRepository) {
        this.unidadAcademicaRepository = unidadAcademicaRepository;
    }

    public List<UnidadAcademica> obtenerUA() {
        return this.unidadAcademicaRepository.findAll();
    }

}
