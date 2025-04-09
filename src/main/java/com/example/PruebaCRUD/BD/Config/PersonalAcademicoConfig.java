package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.BD.TipoPersonal;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.PersonalAcademicoRepository;
import com.example.PruebaCRUD.Repositories.TipoPersonalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class PersonalAcademicoConfig {
    /**
     *
     * @param personalAcademicoRepository Repositorio Spring de PersonalAcademico para manipular dicha tabla en la BD
     * @param personaRepository Repositorio Spring de Persona para manipular dicha tabla en la BD
     * @param tipoPersonalRepository Repositorio Spring de TipoPersonal para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(18) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataPA(PersonalAcademicoRepository personalAcademicoRepository,
                                 PersonaRepository personaRepository,
                                 TipoPersonalRepository tipoPersonalRepository) {
        return args -> {
            if (personalAcademicoRepository.count() == 0) { // Si no encuentra registros entra aquí
                // Busca registros de Persona por CURP, en caso de no encontarlo devuelve null
                Persona Ulises = personaRepository.findPersonaByCURP("10").orElse(null);
                Persona Saul = personaRepository.findPersonaByCURP("11").orElse(null);
                Persona Chadwick = personaRepository.findPersonaByCURP("12").orElse(null);
                Persona Andres = personaRepository.findPersonaByCURP("13").orElse(null);

                // Busca registros de TipoPersonal por ID, en caso de no encontrarlo devuelve null
                TipoPersonal Docente = tipoPersonalRepository.findById(1).orElse(null);

                // Guarda nuevos registros de PersonalAcademico con los datos anteriores
                personalAcademicoRepository.save(new PersonalAcademico("D", Ulises, "ulises@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("E", Saul, "saul@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("F", Chadwick, "chadwick@gmail.com", Docente));
                personalAcademicoRepository.save(new PersonalAcademico("G", Andres, "andres@gmail.com", Docente));
            }
        };
    }
}
