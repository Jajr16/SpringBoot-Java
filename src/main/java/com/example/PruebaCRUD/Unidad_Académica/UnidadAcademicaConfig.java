package com.example.PruebaCRUD.Unidad_Académica;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnidadAcademicaConfig {

    @Bean
    CommandLineRunner initDataUA(UnidadAcademicaRepository unidadAcademicaRepository) {
        return args -> {
            if (unidadAcademicaRepository.count() == 0) {
                unidadAcademicaRepository.save(new UnidadAcademica("ESCOM"));
            }
        };
    }
}
