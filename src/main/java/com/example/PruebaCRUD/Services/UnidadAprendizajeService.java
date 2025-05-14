package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class UnidadAprendizajeService {
    private final UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;

    @Autowired // Notación que permite inyectar dependencias
    public UnidadAprendizajeService(UnidadAprendizajeRepositorio unidadAprendizajeRepositorio) {
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
    }

    public List<UnidadAprendizajeProjectionSaes> getUApren(){
        return this.unidadAprendizajeRepositorio.findAllBy();
    }
}
