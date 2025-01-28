package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.BD.TipoPersonal;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.PersonalAcademicoRepository;
import com.example.PruebaCRUD.Repositories.TipoPersonalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class PersonalAcademicoConfig {
    @Bean
    @Order(18)
    CommandLineRunner initDataPA(PersonalAcademicoRepository personalAcademicoRepository,
                                 PersonaRepository personaRepository,
                                 TipoPersonalRepository tipoPersonalRepository) {
        return args -> {
            if (personalAcademicoRepository.count() == 0) {
                Persona Ulises = personaRepository.findPersonaByCURP("10").orElse(null);
                Persona Saul = personaRepository.findPersonaByCURP("11").orElse(null);
                Persona Chadwick = personaRepository.findPersonaByCURP("12").orElse(null);
                Persona Andres = personaRepository.findPersonaByCURP("13").orElse(null);

                TipoPersonal Docente = tipoPersonalRepository.findById(1).orElse(null);

                personalAcademicoRepository.save(new PersonalAcademico("D", Ulises, "ulises@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("E", Saul, "saul@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("F", Chadwick, "chadwick@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("G", Andres, "andres@gmail.com", Docente));
            }
        };
    }
}
