package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TurnoConfig {
    @Bean
    @Order(10)
    CommandLineRunner initDataTurno(TurnoRepository turnoRepository) {
        return args -> {
            System.out.println("=========== CREACIÓN DE TURNOS (ORDER10) ==============");
            if (turnoRepository.count() == 0) {
                turnoRepository.save(new Turno("Matutino"));
                turnoRepository.save(new Turno("Vespertino"));
            }
        };
    }
}
