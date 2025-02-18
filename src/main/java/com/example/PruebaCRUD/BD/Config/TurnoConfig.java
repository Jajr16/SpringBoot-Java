package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class TurnoConfig {
    /**
     *
     * @param turnoRepository Repositorio Spring de Turno para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(10) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataTurno(TurnoRepository turnoRepository) {
        return args -> {
            if (turnoRepository.count() == 0) { // Si no encunetra registros entra aquí
                // Guarda nuevos registros
                turnoRepository.save(new Turno("Matutino"));
                turnoRepository.save(new Turno("Vespertino"));
            }
        };
    }
}
