package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Cargo;
import com.example.PruebaCRUD.Repositories.CargoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)}
public class CargoConfig {
    /**
     *
     * @param cargoRepository  Reporitorio Spring de la tabla Cargo del personal academico,
     *                        el cual servirá para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(19) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataCargo(CargoRepository cargoRepository) {
        return args -> {
            if (cargoRepository.count() == 0) { // Si aún no hay ningún registro en la tabla cargo entra aquí
                // Guarda nuevos registros con nuevas instancias de Cargo
                cargoRepository.save(new Cargo("Docente"));
                cargoRepository.save(new Cargo("Subdirector"));
                cargoRepository.save(new Cargo("Director"));
                cargoRepository.save(new Cargo("Presidente Academia"));
            }
        };
    }
}
