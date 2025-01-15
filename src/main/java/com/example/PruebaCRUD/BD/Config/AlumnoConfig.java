package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.BD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.BD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.BD.Repositories.ProgramaAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AlumnoConfig {
    @Bean
    @Order(8)
    CommandLineRunner initDataAlumno(AlumnoRepository alumnoRepository,
                                     PersonaRepository personaRepository,
                                     ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {
            if (alumnoRepository.count() == 0) {
                Persona Alfredo = personaRepository.findPersonaByCURP("1").orElseGet(null);
                Persona Ale = personaRepository.findPersonaByCURP("2").orElseGet(null);

                ProgramaAcademico IA = programaAcademicoRepository.findByIdPA("IIA-2024").orElseGet(null);
                ProgramaAcademico ISC = programaAcademicoRepository.findByIdPA("ISC-2024").orElseGet(null);

                alumnoRepository.save(new Alumno("2022630467", Alfredo, "1@gmail.com", ISC, "IMG" ));
                alumnoRepository.save(new Alumno("2022630467", Ale, "1@gmail.com", IA, "IMG" ));
            }
        };
    }
}
