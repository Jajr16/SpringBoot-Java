package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class ProgramaAcademicoConfig {
    /**
     *
     * @param programaAcademicoRepository Repositorio Spring de ProgramaAcademico para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(2) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataProgAcad(ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {
            if (programaAcademicoRepository.count() == 0) { // Si no encuentra ningún registro entra aquí
                // Guarda los nuevos registros de ProgramaAcademico
                programaAcademicoRepository.save(new ProgramaAcademico("IIA-2024",
                        "Ingeniería en Inteligencia Artificial", "Descripcion2"));
                programaAcademicoRepository.save(new ProgramaAcademico("ISC-2024",
                        "Ingeniería en Sistemas Computacionales", "Descripcion1"));
            }
        };
    }
}
