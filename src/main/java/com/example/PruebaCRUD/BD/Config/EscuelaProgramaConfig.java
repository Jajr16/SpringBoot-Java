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

@Configuration
public class EscuelaProgramaConfig {

    @Bean
    @Order(3)
    CommandLineRunner initDataEP(EscuelaProgramaRepository escuelaProgramaRepository,
                                 UnidadAcademicaRepository unidadAcademicaRepository,
                                 ProgramaAcademicoRepository programaAcademicoRepository) {
        return args -> {

            System.out.println("=========== CREACIÓN DE UNIDADES ACADEMICAS (ORDER3)==============");
            UnidadAcademica ua1 = unidadAcademicaRepository.findByNombre("ESCOM").orElse(null);
            if (ua1 == null) {
                System.out.println("=============== ENTRÓ EN LA CREACIÓN DE LA ESCUELA 'ESCOM' ====================");
                ua1 = unidadAcademicaRepository.save(new UnidadAcademica(1, "ESCOM"));
                System.out.println("=============== SE CREÓ 'ESCOM' ====================");
            }

            UnidadAcademica ua2 = unidadAcademicaRepository.findByNombre("ESCA").orElse(null);
            if (ua2 == null) {
                System.out.println("=============== ENTRÓ EN LA CREACIÓN DE LA ESCUELA 'ESCA' ====================");
                ua2 = unidadAcademicaRepository.save(new UnidadAcademica(2, "ESCA"));
                System.out.println("=============== SE CREÓ 'ESCA' ====================");
            }


            System.out.println("=========== CREACIÓN DE PROGRAMAS ACADEMICOS (ORDER3) ==============");
            ProgramaAcademico pa1 = programaAcademicoRepository.findByIdPA("ISC-2024").orElse(null);
            if (pa1 == null) {
                System.out.println("=============== ENTRÓ EN LA CREACIÓN DE 'ISC' ====================");
                pa1 = programaAcademicoRepository.save(new ProgramaAcademico("ISC-2024", "Ingeniería en Sistemas Computacionales", "Descripcion1"));
                System.out.println("=============== SE CREÓ 'ISC' ====================");
            }

            ProgramaAcademico pa2 = programaAcademicoRepository.findByIdPA("IIA-2024").orElse(null);
            if (pa2 == null) {
                System.out.println("=============== ENTRÓ EN LA CREACIÓN DE 'IIA' ====================");
                pa2 = programaAcademicoRepository.save(new ProgramaAcademico("IIA-2024", "Ingeniería en Inteligencia Artificial", "Descripcion2"));
                System.out.println("=============== SE CREÓ 'IIA' ====================");
            }

            System.out.println("=========== CREACIÓN DE LAS RELACIONES DE ESCUELA_PROGRAMA ==============");

            // Ahora, se crean las entidades EscuelaPrograma con las relaciones válidas
            if (escuelaProgramaRepository.count() == 0) {
                EscuelaProgramaPK reg1 = new EscuelaProgramaPK();

                reg1.setIdEscuela(ua1.getIdEscuela()); // Establece la clave foránea de la UnidadAcademica
                reg1.setIdPA(pa1.getId_PA()); // Establece la clave foránea del ProgramaAcademico

                EscuelaPrograma escuelaProgramareg1 = new EscuelaPrograma();
                escuelaProgramareg1.setId(reg1);
                escuelaProgramareg1.setIdEscuela(ua1);
                escuelaProgramareg1.setIdPA(pa1);

                escuelaProgramaRepository.save(escuelaProgramareg1);

                EscuelaProgramaPK reg2 = new EscuelaProgramaPK();


                reg2.setIdEscuela(ua2.getIdEscuela());
                reg2.setIdPA(pa2.getId_PA());

                EscuelaPrograma escuelaProgramareg2 = new EscuelaPrograma(reg2, ua2, pa2); // Asocia las entidades completas
                escuelaProgramaRepository.save(escuelaProgramareg2);
            }
        };
    }
}
