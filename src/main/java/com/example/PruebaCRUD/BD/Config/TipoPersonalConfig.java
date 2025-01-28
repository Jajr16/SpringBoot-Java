package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.TipoPersonal;
import com.example.PruebaCRUD.Repositories.TipoPersonalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TipoPersonalConfig {
    @Bean
    @Order(17)
    CommandLineRunner initDataTP(TipoPersonalRepository tipoPersonalRepository) {
        return args -> {
            if (tipoPersonalRepository.count() == 0) {
                tipoPersonalRepository.save(new TipoPersonal(1, "Docente"));
                tipoPersonalRepository.save(new TipoPersonal(1, "Jefe Gestión Escolar"));
                tipoPersonalRepository.save(new TipoPersonal(1, "Personal Gestión Escolar"));
            }
        };
    }
}
