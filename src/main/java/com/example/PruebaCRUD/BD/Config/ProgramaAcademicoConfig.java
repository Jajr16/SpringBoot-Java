package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.BD.Repositories.ProgramaAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class ProgramaAcademicoConfig {
    @Bean
    @Order(2)
    CommandLineRunner initDataProgAcad(ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {
            if (programaAcademicoRepository.count() == 0) {
                programaAcademicoRepository.save(new ProgramaAcademico("ISC-2024", "Ingeniería en Sistemas Computacionales", "Descripcion1"));
                programaAcademicoRepository.save(new ProgramaAcademico("IIA-2024", "Ingeniería en Inteligencia Artificial", "Descripcion2"));
            }
        };
    }
}
