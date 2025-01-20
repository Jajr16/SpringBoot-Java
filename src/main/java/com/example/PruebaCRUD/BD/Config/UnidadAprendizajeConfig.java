package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class UnidadAprendizajeConfig {
    @Bean
    @Order(11)
    CommandLineRunner initDataUAprendizaje(ProgramaAcademicoRepository programaAcademicoRepository,
                                 UnidadAprendizajeRepository unidadAprendizajeRepository) {
        return args -> {
            System.out.println("=========== CREACIÓN DE UNIDAD DE APRENDIZAJE (ORDER11)==============");

            if (unidadAprendizajeRepository.count() == 0) {
                ProgramaAcademico isc = programaAcademicoRepository.findByIdPA("ISC-2024").orElse(null);
                ProgramaAcademico iia = programaAcademicoRepository.findByIdPA("IIA-2024").orElse(null);

                unidadAprendizajeRepository.save(new UnidadAprendizaje("PDI-IIA", "Programación Digital de Imágenes",
                        "Una materia", iia));

                unidadAprendizajeRepository.save(new UnidadAprendizaje("BBD-ISC", "BigData",
                        "Una materia", isc));

                unidadAprendizajeRepository.save(new UnidadAprendizaje("BD-IIA", "Bases de Datos",
                        "Una materia", iia));
            }
        };
    }
}
