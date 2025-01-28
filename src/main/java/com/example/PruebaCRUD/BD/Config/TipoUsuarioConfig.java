package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.Repositories.TipoUsuarioRepository;
import com.example.PruebaCRUD.BD.TipoUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TipoUsuarioConfig {
    @Bean
    @Order(6)
    CommandLineRunner initDataTipoU(TipoUsuarioRepository tipoUsuarioRepository) {
        return args -> {
            if (tipoUsuarioRepository.count() == 0) {
                tipoUsuarioRepository.save(new TipoUsuario(1, "Alumno"));
                tipoUsuarioRepository.save(new TipoUsuario(2, "Personal Academico"));
                tipoUsuarioRepository.save(new TipoUsuario(3, "Personal Seguridad"));
            }
        };
    }
}
