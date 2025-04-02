package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class CargoService {
    private final CargoRepository cargoRepository;

    @Autowired // Notación que permite inyectar dependencias, en este caso, CargoRepository
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<?> getCargos() {
        return this.cargoRepository.findAllBy();
    }
}
