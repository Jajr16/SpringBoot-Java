package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.BD.Repositories.SexoRepository;
import com.example.PruebaCRUD.BD.Repositories.UnidadAcademicaRepository;
import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class PersonaConfig {
    @Bean
    @Order(5)
    CommandLineRunner initDataPersona(PersonaRepository personaRepository,
                                      SexoRepository sexoRepository,
                                      UnidadAcademicaRepository unidadAcademicaRepository) {
        return args -> {

            if (personaRepository.count() == 0) {
//                Obtener ambos sexos
                List<Sexo> sexos = sexoRepository.findAll();
                Sexo masculino = sexos.stream().filter(s -> s.getNombre().equalsIgnoreCase("Masculino")).findFirst().orElseGet(() ->
                        sexoRepository.save(new Sexo("Masculino"))
                );
                Sexo femenino = sexos.stream().filter(s -> s.getNombre().equalsIgnoreCase("Femenino")).findFirst().orElseGet(() ->
                        sexoRepository.save(new Sexo("Femenino"))
                );

                UnidadAcademica ESCOM = unidadAcademicaRepository.findByNombre("ESCOM").orElseGet(() ->
                    unidadAcademicaRepository.save(new UnidadAcademica("ESCOM"))
                );

                UnidadAcademica ESCA = unidadAcademicaRepository.findByNombre("ESCA").orElseGet(() ->
                        unidadAcademicaRepository.save(new UnidadAcademica(("ESCA")))
                );


                personaRepository.save(new Persona("1", "José Alfredo", "Jiménez", "Rodríguez", masculino, ESCOM));
                personaRepository.save(new Persona("2", "Alejandra", "De la cruz", "De la cruz", femenino, ESCOM));
                personaRepository.save(new Persona("3", "Luis Antonio", "Flores", "Esquivel", masculino, ESCOM));
                personaRepository.save(new Persona("4", "Daniel Martin", "Huertas", "Ramírez", masculino, ESCOM));
            }
        };
    }
}
