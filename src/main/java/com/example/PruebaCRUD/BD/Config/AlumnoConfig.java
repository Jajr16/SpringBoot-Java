package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
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
            System.out.println("=========== CREACIÓN DE ALUMNOS (ORDER8) ==============");
            if (alumnoRepository.count() == 0) {
                Persona Alfredo = personaRepository.findPersonaByCURP("1").orElseGet(null);
                Persona Ale = personaRepository.findPersonaByCURP("2").orElseGet(null);
                Persona Flores = personaRepository.findPersonaByCURP("3").orElseGet(null);
                Persona Daniel = personaRepository.findPersonaByCURP("4").orElseGet(null);
                Persona Luis = personaRepository.findPersonaByCURP("5").orElseGet(null);
                Persona Roman = personaRepository.findPersonaByCURP("6").orElseGet(null);

                ProgramaAcademico IA = programaAcademicoRepository.findByIdPA("IIA-2024").orElseGet(null);
                ProgramaAcademico ISC = programaAcademicoRepository.findByIdPA("ISC-2024").orElseGet(null);

                alumnoRepository.save(new Alumno("2022630467", Alfredo, "1@gmail.com", IA, "IMG" ));
                alumnoRepository.save(new Alumno("1234567890", Ale, "2@gmail.com", IA, "IMG" ));
                alumnoRepository.save(new Alumno("0987654321", Flores, "3@gmail.com", IA, "IMG" ));
                alumnoRepository.save(new Alumno("0123456789", Daniel, "4@gmail.com", IA, "IMG" ));
                alumnoRepository.save(new Alumno("1111111111", Luis, "5@gmail.com", IA, "IMG" ));
                alumnoRepository.save(new Alumno("0000000000", Roman, "6@gmail.com", ISC, "IMG" ));
            }
        };
    }
}
