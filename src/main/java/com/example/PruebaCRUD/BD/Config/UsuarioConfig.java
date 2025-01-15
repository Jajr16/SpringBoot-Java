package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.BD.Repositories.TipoUsuarioRepository;
import com.example.PruebaCRUD.BD.Repositories.UsuarioRepository;
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

                usuarioRepository.save(new Usuario("2022630467", "$2b$12$KejElPRgHbWDWF2BmlukSOMb8rqEzhNSVBbgndRPbhU.YqdDxI8US", Alumno, Alfredo));
                usuarioRepository.save(new Usuario("2022325410", "$2b$12$KejElPRgHbWDWF2BmlukSOMb8rqEzhNSVBbgndRPbhU.YqdDxI8US", Alumno, Ale));
            }
        };
    }
}
