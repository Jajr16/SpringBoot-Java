package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Salon;
import com.example.PruebaCRUD.BD.TipoSalon;
import com.example.PruebaCRUD.Repositories.SalonRepository;
import com.example.PruebaCRUD.Repositories.TipoSalonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class SalonesConfig {
    @Bean
    @Order(15)
    CommandLineRunner initDataSalones(SalonRepository salonRepository,
                                      TipoSalonRepository tipoSalonRepository) {
        return args -> {
            if (salonRepository.count() == 0 ) {
                TipoSalon lab = tipoSalonRepository.findByTipo("Laboratorio").orElse(null);
                TipoSalon normal = tipoSalonRepository.findByTipo("Normal").orElse(null);

                salonRepository.save(new Salon(4108, 4, 1, lab));
                salonRepository.save(new Salon(3210, 3, 2, normal));
                salonRepository.save(new Salon(1111, 1, 1, normal));
            }
        };
    }
}
