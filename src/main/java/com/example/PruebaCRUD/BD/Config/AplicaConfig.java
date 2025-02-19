package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.*;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.AplicaRepository;
import com.example.PruebaCRUD.Repositories.DocentemRepository;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class AplicaConfig {
    /**
     *
     * @param etsRepository Repositorio Spring de ETS para manipular dicha tabla en la BD
     * @param aplicaRepository d
     * @param docentemRepository d
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(22) // Orden en el que se ejecutará este fragmento de código

    CommandLineRunner initDataAplica(ETSRepository etsRepository, AplicaRepository aplicaRepository,
                                     DocentemRepository docentemRepository) {

        return args -> {

            PersonalAcademico docente = docentemRepository.findByRfc("D").orElse(null);

            // Busca registros de ETS por ID, en caso de no encontrarlo devuelve null
            ETS ets1 = etsRepository.findById(1).orElse(null);
            ETS ets2 = etsRepository.findById(2).orElse(null);
            ETS ets3 = etsRepository.findById(3).orElse(null);

            // Instancia de la llave primaria de InscripcionETS (Aquí se deben de llenar las variables normales)
            AplicaPK ietspk1 = new AplicaPK();
            assert ets1 != null; // Afirma que la variable Ulises no sea null
            ietspk1.setIdETS(ets1.getIdETS()); // Se asigna el ID del ETS
            assert docente != null; // Afirma que la variable Ulises no sea null
            ietspk1.setDocenteRFC(docente.getRFC()); // Se asigna la Boleta del Alumno

            /**
             * Se hace la instancia de InscripcionETS (tabla princiapl), como se mencionó, se deben de asignar todas
             * las variables de dicha clase, las cuales son instancias de otras clases.
             */
            Aplica ins1 = new Aplica();
            ins1.setId(ietspk1);
            ins1.setDocenteRFC(docente);
            ins1.setTitular(true);
            ins1.setIdETS(ets1);


            // Se guarda el registro anterior
            aplicaRepository.save(ins1);

        };


    }

}
