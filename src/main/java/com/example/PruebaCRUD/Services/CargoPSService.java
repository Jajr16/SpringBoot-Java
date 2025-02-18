package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.Saes.CargoPSProjectionSaes;
import com.example.PruebaCRUD.Repositories.CargoPSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class CargoPSService {
    private final CargoPSRepository cargoPSRepository;

    @Autowired // Notación que permite inyectar dependencias, en este caso, CargoPSRepository
    public CargoPSService(CargoPSRepository cargoPSRepository) {
        this.cargoPSRepository = cargoPSRepository;
    }

    public List<?> getCargos() {
        return this.cargoPSRepository.findAllBy();
    };
}
