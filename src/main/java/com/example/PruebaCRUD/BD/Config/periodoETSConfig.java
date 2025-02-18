package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.periodoETS;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class periodoETSConfig {
    /**
     *
     * @param pETSRepository Repositorio Spring de periodoETS para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(9) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataperiodoETS(periodoETSRepository pETSRepository) {
        return args -> {
            if (pETSRepository.count() == 0) { // Si no encuentra registros entra aquí

                // Aquí se debe de darle formato a la fecha que vamos a guardar para que se pase correctamente a la BD
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
                // Nueva variable fechaInicio con formato anterior
                Date fechaInicio = dateFormat.parse("2025-02-10");
                Date fechaFin = dateFormat.parse("2025-05-10"); // Nueva variable fechaFin con formato anterior

                // Guarda nuevo registro periodoETS con los datos anteriores
                pETSRepository.save(new periodoETS("25/1", 'O', fechaInicio, fechaFin));
            }
        };
    }
}
