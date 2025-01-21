package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class ETSConfig {
    @Bean
    @Order(12)
    CommandLineRunner initDataETS(ETSRepository etsRepository, TurnoRepository turnoRepository,
                                  periodoETSRepository periodoETSRepository,
                                  UnidadAprendizajeRepository unidadAprendizajeRepository) {
        return args -> {
            System.out.println("=========== CREACIÓN DE ETS (ORDER12)==============");
            if (etsRepository.count() == 0) {
                Turno Matutino = turnoRepository.findByNombre("Matutino").orElse(null);
                Turno Vespertino = turnoRepository.findByNombre("Vespertino").orElse(null);

                periodoETS periodo = periodoETSRepository.findByPeriodoAndTipo("25/1", 'O').orElse(null);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = dateFormat.parse("2024-10-04");

                UnidadAprendizaje BD = unidadAprendizajeRepository.findById("BD-IIA").orElse(null);
                UnidadAprendizaje PDI = unidadAprendizajeRepository.findById("PDI-IIA").orElse(null);
                UnidadAprendizaje BBD = unidadAprendizajeRepository.findById("BBD-ISC").orElse(null);

                etsRepository.save(new ETS(periodo, Matutino, fecha, 20, BD, 2));
                etsRepository.save(new ETS(periodo, Vespertino, fecha, 20, PDI, 2));
                etsRepository.save(new ETS(periodo, Matutino, fecha, 20, BBD, 2));
            }
        };
    }
}
