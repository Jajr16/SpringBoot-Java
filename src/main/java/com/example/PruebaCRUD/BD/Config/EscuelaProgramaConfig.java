package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.EscuelaProgramaRepository;
import com.example.PruebaCRUD.Repositories.UnidadAcademicaRepository;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class EscuelaProgramaConfig {
    /**
     *
     * @param escuelaProgramaRepository Reporitorio Spring de EscuelaPrograma el cual servirá para manipular dicha tabla
     *                                  en la BD
     * @param unidadAcademicaRepository Reporitorio Spring de UnidadAcademica el cual servirá para manipular dicha tabla
     *                                  en la BD
     * @param programaAcademicoRepository Reporitorio Spring de ProgramaAcademico el cual servirá para manipular dicha
     *                                   tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(3) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataEP(EscuelaProgramaRepository escuelaProgramaRepository,
                                 UnidadAcademicaRepository unidadAcademicaRepository,
                                 ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {

            // Busca registros de UnidadAcademica por su nombre, en caso de no encontrarlo devuelve null
            UnidadAcademica ua1 = unidadAcademicaRepository.findByNombre("ESCOM").orElse(null);
            if (ua1 == null) { // Si no encuentra regsitros entra aquí
                // Guarda un nuevo registro de UnidadAcademica
                ua1 = unidadAcademicaRepository.save(new UnidadAcademica(1, "ESCOM"));
            }

            // Busca registros de UnidadAcademica por su nombre, en caso de no encontrarlo devuelve null
            UnidadAcademica ua2 = unidadAcademicaRepository.findByNombre("ESCA").orElse(null);
            if (ua2 == null) { // Si no encuentra regsitros entra aquí
                // Guarda un nuevo registro de UnidadAcademica
                ua2 = unidadAcademicaRepository.save(new UnidadAcademica(2, "ESCA"));
            }

            // Busca registros de ProgramaAcademico por su ID, en caso de no encontrarlo devuelve null
            ProgramaAcademico pa1 = programaAcademicoRepository.findByIdPA("ISC-2020").orElse(null);
            if (pa1 == null) { // Si no encuentra registros entra aquí
                // Guarda un nuevo registro de ProgramaAcademico
                pa1 = programaAcademicoRepository.save(new ProgramaAcademico("ISC-2020",
                        "Ingeniería en Sistemas Computacionales", "Descripcion1"));
            }

            // Busca registros de ProgramaAcademico por su ID, en caso de no encontrarlo devuelve null
            ProgramaAcademico pa2 = programaAcademicoRepository.findByIdPA("IIA-2020").orElse(null);
            if (pa2 == null) { // Si no encuentra registros entra aquí
                // Guarda un nuevo registro de ProgramaAcademico
                pa2 = programaAcademicoRepository.save(new ProgramaAcademico("IIA-2020",
                        "Ingeniería en Inteligencia Artificial", "Descripcion2"));
            }
            // Busca registros de ProgramaAcademico por su ID, en caso de no encontrarlo devuelve null
            ProgramaAcademico pa3 = programaAcademicoRepository.findByIdPA("LCD-2020").orElse(null);
            if (pa3 == null) { // Si no encuentra registros entra aquí
                // Guarda un nuevo registro de ProgramaAcademico
                pa3 = programaAcademicoRepository.save(new ProgramaAcademico("LCD-2020",
                        "Licenciatura en Ciencia de Datos", "Descripcion3"));
            }


            // Ahora, se crean las entidades EscuelaPrograma con las relaciones válidas
            if (escuelaProgramaRepository.count() == 0) {
                // Se crea una instancia de la clase EscuelaProgramaPK (Llave primaria compuesta de EscuelaPrograma)
                EscuelaProgramaPK reg1 = new EscuelaProgramaPK();

                reg1.setIdEscuela(ua1.getIdEscuela()); // Establece la clave foránea de la UnidadAcademica
                reg1.setIdPA(pa1.getId_PA()); // Establece la clave foránea del ProgramaAcademico

                // Se crea una instancia de la clase EscuelaPrograma
                EscuelaPrograma escuelaProgramareg1 = new EscuelaPrograma();
                escuelaProgramareg1.setId(reg1); // Se asigna la llave primaria compuesta
                escuelaProgramareg1.setIdEscuela(ua1); // Se asigna la escuela
                escuelaProgramareg1.setIdPA(pa1); // Se asigna el Programa Academico

                escuelaProgramaRepository.save(escuelaProgramareg1); // Guarda el nuevo registro


                EscuelaProgramaPK reg2 = new EscuelaProgramaPK();

                reg2.setIdEscuela(ua1.getIdEscuela());
                reg2.setIdPA(pa2.getId_PA());

                EscuelaPrograma escuelaProgramareg2 = new EscuelaPrograma(reg2, ua1, pa2); // Asocia las entidades completas
                escuelaProgramaRepository.save(escuelaProgramareg2);


                EscuelaProgramaPK reg3 = new EscuelaProgramaPK();
                reg3.setIdEscuela(ua1.getIdEscuela());
                reg3.setIdPA(pa3.getId_PA());
                EscuelaPrograma escuelaProgramareg3 = new EscuelaPrograma(reg3, ua1, pa3);
                escuelaProgramaRepository.save(escuelaProgramareg3);
            }
        };
    }
}
