package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Repositories.TipoUsuarioRepository;
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
                tipoUsuarioRepository.save(new TipoUsuario("Alumno"));
                tipoUsuarioRepository.save(new TipoUsuario("Docente"));
                tipoUsuarioRepository.save(new TipoUsuario("Personal Seguridad"));
            }
        };
    }
}
