package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class AlumnoConfig {
    /**
     * Ejecuta el código para llenar la tabla Alumno una vez inicializada la aplicación
     *
     * @param alumnoRepository Reporitorio Spring del alumno el cual servirá para manipular dicha tabla en la BD
     * @param personaRepository Reporitorio Spring de la persona el cual servirá para manipular dicha tabla en la BD
     * @param programaAcademicoRepository Reporitorio Spring del programa academico
     *                                   el cual servirá para manipular dicha tabla en la BD
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(8) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataAlumno(AlumnoRepository alumnoRepository,
                                     PersonaRepository personaRepository,
                                     ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {
            System.out.println("=========== CREACIÓN DE ALUMNOS (ORDER8) ==============");
            if (alumnoRepository.count() == 0) { // Si aún no hay ningún registro en la tabla alumno entra aquí
                // Busca el registro de cada uno de los Alumnos por CURP, en caso de no encontrarlo devuelve null
                Persona Alfredo = personaRepository.findPersonaByCURP("1").orElseGet(null);
//                Persona Ale = personaRepository.findPersonaByCURP("2").orElseGet(null);
//                Persona Flores = personaRepository.findPersonaByCURP("3").orElseGet(null);
//                Persona Daniel = personaRepository.findPersonaByCURP("4").orElseGet(null);
//                Persona Luis = personaRepository.findPersonaByCURP("5").orElseGet(null);
//                Persona Roman = personaRepository.findPersonaByCURP("6").orElseGet(null);

                // Busca el registro de cada programa académico por su ID, en caso de no encontrarlo devuelve null
                ProgramaAcademico IA = programaAcademicoRepository.findByIdPA("IIA-2024").orElseGet(null);
                ProgramaAcademico ISC = programaAcademicoRepository.findByIdPA("ISC-2024").orElseGet(null);

                // Guarda nuevos registros con nuevas instancias de Alumno y con los registros obtenidos anteriormente
                alumnoRepository.save(new Alumno("2022630467", Alfredo, "1@gmail.com", IA, "IMG" ));
                //alumnoRepository.save(new Alumno("1234567890", Ale, "2@gmail.com", IA, "IMG" ));
                //alumnoRepository.save(new Alumno("0987654321", Flores, "3@gmail.com", IA, "IMG" ));
                //alumnoRepository.save(new Alumno("0123456789", Daniel, "4@gmail.com", IA, "IMG" ));
                //alumnoRepository.save(new Alumno("1111111111", Luis, "5@gmail.com", IA, "IMG" ));
                //alumnoRepository.save(new Alumno("0000000000", Roman, "6@gmail.com", ISC, "IMG" ));
            }
        };
    }
}
