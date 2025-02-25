package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import com.example.PruebaCRUD.Repositories.UnidadAprendizajeRepository;
import com.example.PruebaCRUD.Repositories.periodoETSRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class ETSConfig {
    /**
     *
     * @param etsRepository Repositorio Spring de ETS para manipular dicha tabla en la BD
     * @param turnoRepository Repositorio Spring de Turno para manipular dicha tabla en la BD
     * @param periodoETSRepository Repositorio Spring de periodoETS para manipular dicha tabla en la BD
     * @param unidadAprendizajeRepository Repositorio Spring de UnidadAprendizaje para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(12) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataETS(ETSRepository etsRepository, TurnoRepository turnoRepository,
                                  periodoETSRepository periodoETSRepository,
                                  UnidadAprendizajeRepository unidadAprendizajeRepository) {
        return args -> {
            if (etsRepository.count() == 0) { // Si no encuentra registros entra aquí
                // Busca registros de Turno por su nombre, en caso de no encontrarlo devuelve null
                Turno Matutino = turnoRepository.findByNombre("Matutino").orElse(null);
                Turno Vespertino = turnoRepository.findByNombre("Vespertino").orElse(null);

                // Busca registros de PeriodoETS por periodo y tipo, en caso de no encontrarlo devuelve null
                periodoETS periodo = periodoETSRepository.findByPeriodoAndTipo("25/2", 'O').orElse(null);

                // Aquí se debe de darle formato a la fecha que vamos a guardar para que se pase correctamente a la BD
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha
                Date fecha = dateFormat.parse("2025-07-14"); // Nueva variable fecha con formato anterior

                // Busca registros de UnidadAprendizaje por ID, en caso de no encontrarlo devuelve null
                UnidadAprendizaje BD = unidadAprendizajeRepository.findById("BD-IIA").orElse(null);
                UnidadAprendizaje PDI = unidadAprendizajeRepository.findById("PDI-IIA").orElse(null);
                UnidadAprendizaje BBD = unidadAprendizajeRepository.findById("BBD-ISC").orElse(null);

                // Guarda nuevos registros con nuevas instancias de ETS
                etsRepository.save(new ETS(periodo, Matutino, fecha, 20, BD, 2));
                etsRepository.save(new ETS(periodo, Vespertino, fecha, 20, PDI, 2));
                etsRepository.save(new ETS(periodo, Matutino, fecha, 20, BBD, 2));
            }
        };
    }
}
