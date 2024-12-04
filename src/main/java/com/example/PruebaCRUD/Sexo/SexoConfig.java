package com.example.PruebaCRUD.Sexo;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SexoConfig {

    @Bean
    CommandLineRunner initDataSex(SexoRepository sexoRepository) {
        return args -> {
            if (sexoRepository.count() == 0) {
                sexoRepository.save(new Sexo("Masculino"));
                sexoRepository.save(new Sexo("Femenino"));
            }
        };
    }
}
