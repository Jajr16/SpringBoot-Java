package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.BD.Repositories.SexoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class SexoConfig {

    @Bean
    @Order(4)
    CommandLineRunner initDataSex(SexoRepository sexoRepository) {
        return args -> {
            if (sexoRepository.count() == 0) {
                sexoRepository.save(new Sexo("Masculino"));
                sexoRepository.save(new Sexo("Femenino"));
            }
        };
    }
}
