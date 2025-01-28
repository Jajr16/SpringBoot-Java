package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.periodoETS;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class periodoETSConfig {
    @Bean
    @Order(9)
    CommandLineRunner initDataperiodoETS(periodoETSRepository pETSRepository) {
        return args -> {
            if (pETSRepository.count() == 0) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaInicio = dateFormat.parse("2025-02-10");
                Date fechaFin = dateFormat.parse("2025-05-10");

                pETSRepository.save(new periodoETS("25/1", 'O', fechaInicio, fechaFin));
            }
        };
    }
}
