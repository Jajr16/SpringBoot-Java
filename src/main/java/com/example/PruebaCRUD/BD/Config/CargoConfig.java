package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Cargo;
import com.example.PruebaCRUD.Repositories.CargoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class CargoConfig {
    @Bean
    @Order(19)
    CommandLineRunner initDataCargo(CargoRepository cargoRepository) {
        return args -> {
            if (cargoRepository.count() == 0) {
                cargoRepository.save(new Cargo("Docente"));
                cargoRepository.save(new Cargo("Subdirector"));
                cargoRepository.save(new Cargo("Director"));
            }
        };
    }
}
