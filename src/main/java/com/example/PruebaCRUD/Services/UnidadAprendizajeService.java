package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.Saes.UnidadAprendizajeProjectionSaes;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class UnidadAprendizajeService {
    private final UnidadAprendizajeRepository unidadAprendizajeRepository;

    @Autowired // Notación que permite inyectar dependencias
    public UnidadAprendizajeService(UnidadAprendizajeRepository unidadAprendizajeRepository) {
        this.unidadAprendizajeRepository = unidadAprendizajeRepository;
    }

    public List<UnidadAprendizajeProjectionSaes> getUApren(){
        return this.unidadAprendizajeRepository.findAllBy();
    }
}
