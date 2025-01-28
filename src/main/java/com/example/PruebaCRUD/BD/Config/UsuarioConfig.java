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
                TipoUsuario Docente = tipoUsuarioRepository.findByTipo("Personal Academico").orElseGet(() ->
                        tipoUsuarioRepository.save(new TipoUsuario("Personal Academico"))
                );
                TipoUsuario PS = tipoUsuarioRepository.findByTipo("Personal Seguridad").orElseGet(() ->
                        tipoUsuarioRepository.save(new TipoUsuario("Personal Seguridad"))
                );

                Persona Alfredo = personaRepository.findPersonaByCURP("1").orElseGet(null);
                Persona Ale = personaRepository.findPersonaByCURP("2").orElseGet(null);
                Persona Luis = personaRepository.findPersonaByCURP("3").orElseGet(null);
                Persona Daniel = personaRepository.findPersonaByCURP("4").orElseGet(null);
                Persona Valle = personaRepository.findPersonaByCURP("5").orElseGet(null);
                Persona Raman = personaRepository.findPersonaByCURP("6").orElseGet(null);
                Persona Esteban = personaRepository.findPersonaByCURP("7").orElseGet(null);
                Persona Flor = personaRepository.findPersonaByCURP("8").orElseGet(null);
                Persona Carlos = personaRepository.findPersonaByCURP("9").orElseGet(null);
                Persona Ulises = personaRepository.findPersonaByCURP("10").orElseGet(null);
                Persona Saul = personaRepository.findPersonaByCURP("11").orElseGet(null);
                Persona Chadwick = personaRepository.findPersonaByCURP("12").orElseGet(null);
                Persona Andres = personaRepository.findPersonaByCURP("13").orElseGet(null);

                usuarioRepository.save(new Usuario("2022630467", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Alfredo));
                usuarioRepository.save(new Usuario("1234567890", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Ale));
                usuarioRepository.save(new Usuario("0987654321", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Luis));
                usuarioRepository.save(new Usuario("0123456789", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Daniel));
                usuarioRepository.save(new Usuario("1111111111", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Valle));
                usuarioRepository.save(new Usuario("0000000000", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Alumno, Raman));
                usuarioRepository.save(new Usuario("A", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", PS, Esteban));
                usuarioRepository.save(new Usuario("B", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", PS, Flor));
                usuarioRepository.save(new Usuario("C", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", PS, Carlos));
                usuarioRepository.save(new Usuario("D", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Docente, Ulises));
                usuarioRepository.save(new Usuario("E", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Docente, Saul));
                usuarioRepository.save(new Usuario("F", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Docente, Chadwick));
                usuarioRepository.save(new Usuario("G", "$2a$06$6kdBNCvQRuFhwi4bw73aD.KThSE74J6/O2D3fuTBI6EtHjhuu4RCm", Docente, Andres));
            }
        };
    }
}
