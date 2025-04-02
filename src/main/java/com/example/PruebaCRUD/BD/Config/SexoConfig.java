package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Sexo;
import com.example.PruebaCRUD.Repositories.SexoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class SexoConfig {
    /**
     *
     * @param sexoRepository Repositorio Spring de Sexo para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(4) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataSex(SexoRepository sexoRepository) {
        return args -> {
            if (sexoRepository.count() == 0) { // Si no encuentra registros entra aquí
                // Guarda nuevos registros
                sexoRepository.save(new Sexo("Masculino"));
                sexoRepository.save(new Sexo("Femenino"));
            }
        };
    }
}
