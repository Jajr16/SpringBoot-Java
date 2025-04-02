package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class UnidadAprendizajeConfig {
    /**
     *
     * @param programaAcademicoRepository Repositorio Spring de ProgramaAcademico para manipular dicha tabla en la BD
     * @param unidadAprendizajeRepository Repositorio Spring de UnidadAprendizaje para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(11) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataUAprendizaje(ProgramaAcademicoRepository programaAcademicoRepository,
                                 UnidadAprendizajeRepository unidadAprendizajeRepository) {
        return args -> {
            if (unidadAprendizajeRepository.count() == 0) { // Si no encuentra registros entra aquí
                // Busca registros de ProgramaAcademico por ID, si no encuentra nada devuelve null
                ProgramaAcademico isc = programaAcademicoRepository.findByIdPA("ISC-2024").orElse(null);
                ProgramaAcademico iia = programaAcademicoRepository.findByIdPA("IIA-2024").orElse(null);

                // Guarda los nuevos registros con los datos anteriores
                unidadAprendizajeRepository.save(new UnidadAprendizaje("PDI-IIA",
                        "Programación Digital de Imágenes", "Una materia", iia));

                unidadAprendizajeRepository.save(new UnidadAprendizaje("BBD-ISC", "BigData",
                        "Una materia", isc));

                unidadAprendizajeRepository.save(new UnidadAprendizaje("BD-IIA", "Bases de Datos",
                        "Una materia", iia));
            }
        };
    }
}
