package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class SalonService {
    private final SalonRepository salonRepository;

    @Autowired // Notación que permite inyectar dependencias
    public SalonService(SalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    public List<?> getSalonesToETS() {
        return this.salonRepository.findAllBy();
    }

}
