package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.TipoUsuarioRepository;
import com.example.PruebaCRUD.Repositories.UsuarioRepository;
import com.example.PruebaCRUD.BD.TipoUsuario;
import com.example.PruebaCRUD.BD.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class UsuarioConfig {
    @Bean
    @Order(7)
    CommandLineRunner initDataUsuario(UsuarioRepository usuarioRepository,
                                      TipoUsuarioRepository tipoUsuarioRepository,
                                      PersonaRepository personaRepository) {
        return args -> {
            if(usuarioRepository.count() == 0) {
                TipoUsuario Alumno = tipoUsuarioRepository.findByTipo("Alumno").orElseGet(() ->
                        tipoUsuarioRepository.save(new TipoUsuario("Alumno"))
                );
                TipoUsuario Docente = tipoUsuarioRepository.findByTipo("Docente").orElseGet(() ->
                        tipoUsuarioRepository.save(new TipoUsuario("Docente"))
                );
                TipoUsuario PS = tipoUsuarioRepository.findByTipo("Personal Seguridad").orElseGet(() ->
                        tipoUsuarioRepository.save(new TipoUsuario("Personal Seguridad"))
                );

                Persona Alfredo = personaRepository.findPersonaByCURP("1").orElseGet(null);
                Persona Ale = personaRepository.findPersonaByCURP("2").orElseGet(null);

                usuarioRepository.save(new Usuario("2022630467", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Alfredo));
                usuarioRepository.save(new Usuario("2022325410", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Ale));
            }
        };
    }
}
