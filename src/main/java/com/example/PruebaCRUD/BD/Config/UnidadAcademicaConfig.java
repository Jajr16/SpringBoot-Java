package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.UnidadAcademica;
import com.example.PruebaCRUD.BD.Repositories.UnidadAcademicaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class UnidadAcademicaConfig {

    @Bean
    @Order(1)
    CommandLineRunner initDataUA(UnidadAcademicaRepository unidadAcademicaRepository) {
        return args -> {
            if (unidadAcademicaRepository.count() == 0) {
                unidadAcademicaRepository.save(new UnidadAcademica("ESCOM"));
                unidadAcademicaRepository.save(new UnidadAcademica(("ESCA")));
            }
        };
    }
}
