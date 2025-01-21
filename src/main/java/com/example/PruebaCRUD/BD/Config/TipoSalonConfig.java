package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.TipoSalon;
import com.example.PruebaCRUD.Repositories.TipoSalonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TipoSalonConfig {
    @Bean
    @Order(14)
    CommandLineRunner initDataTipoSalon(TipoSalonRepository tipoSalonRepository) {
        return args -> {
            if (tipoSalonRepository.count() == 0) {
                tipoSalonRepository.save(new TipoSalon(1, "Laboratorio"));
                tipoSalonRepository.save(new TipoSalon(2, "Normal"));
            }
        };
    }
}
