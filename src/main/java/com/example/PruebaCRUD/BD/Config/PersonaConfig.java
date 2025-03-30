package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.SexoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAcademicaRepository;
import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class PersonaConfig {
    /**
     *
     * @param personaRepository Repositorio Spring de Persona para manipular dicha tabla en la BD
     * @param sexoRepository Repositorio Spring de Sexo para manipular dicha tabla en la BD
     * @param unidadAcademicaRepository Repositorio Spring de UnidadAcademica para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(5) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataPersona(PersonaRepository personaRepository,
                                      SexoRepository sexoRepository,
                                      UnidadAcademicaRepository unidadAcademicaRepository) {
        return args -> {
            // Si no encuentra ningún registro entra aquí
            if (personaRepository.count() == 0) {
                // Se obtienen todos los registros de la tabla
                List<Sexo> sexos = sexoRepository.findAll();
                // Se filtra por nombre para obtener los registros por separado
                Sexo masculino = sexos.stream().filter(s -> s.getNombre().equalsIgnoreCase("Masculino"))
                        .findFirst().orElseGet(() ->
                        sexoRepository.save(new Sexo("Masculino"))
                );
                Sexo femenino = sexos.stream().filter(s -> s.getNombre().equalsIgnoreCase("Femenino"))
                        .findFirst().orElseGet(() ->
                        sexoRepository.save(new Sexo("Femenino"))
                );

                // Se busca un registro de UnidadAcademica por nombre, en caso de no encontrarlo se crea
                UnidadAcademica ESCOM = unidadAcademicaRepository.findByNombre("ESCOM").orElseGet(() ->
                    unidadAcademicaRepository.save(new UnidadAcademica("ESCOM"))
                );
                // Se busca un registro de UnidadAcademica por nombre, en caso de no encontrarlo se crea
                UnidadAcademica ESCA = unidadAcademicaRepository.findByNombre("ESCA").orElseGet(() ->
                        unidadAcademicaRepository.save(new UnidadAcademica(("ESCA")))
                );

                // Se guardan todos los registros con ayuda de los datos anteriores
                personaRepository.save(new Persona("1", "José Alfredo", "Jiménez", "Rodríguez", masculino, ESCOM));
                personaRepository.save(new Persona("2", "Alejandra", "De la cruz", "De la cruz", femenino, ESCOM));
//                personaRepository.save(new Persona("3", "Luis Antonio", "Flores", "Esquivel", masculino, ESCOM));
//                personaRepository.save(new Persona("4", "Daniel Martin", "Huertas", "Ramírez", masculino, ESCOM));
//                personaRepository.save(new Persona("5", "Luis Fernando", "Valle", "Hernández", masculino, ESCOM));
//                personaRepository.save(new Persona("6", "Román Esteban", "Leyva", "Martínez", masculino, ESCOM));
                personaRepository.save(new Persona("7", "Esteban", "Ramírez", "Gutierrez", masculino, ESCOM));
                personaRepository.save(new Persona("8", "Flor", "Hernández", "Gutierrez", femenino, ESCOM));
                personaRepository.save(new Persona("9", "Carlos", "Rivera", "Martínez", femenino, ESCOM));
                personaRepository.save(new Persona("10", "Ulises", "Velez", "Saldaña", masculino, ESCOM));
                personaRepository.save(new Persona("11", "Saúl", "De la O", "Torres", masculino, ESCOM));
                personaRepository.save(new Persona("12", "Chadwick", "Carreto", "Arrellano", masculino, ESCOM));
                personaRepository.save(new Persona("13", "Andrés", "García", "Floriano", masculino, ESCOM));
            }
        };
    }
}
