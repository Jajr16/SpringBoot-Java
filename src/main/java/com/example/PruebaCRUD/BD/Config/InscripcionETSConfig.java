package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class InscripcionETSConfig {
    @Bean
    @Order(13)
    CommandLineRunner initDataInscripcionETS(AlumnoRepository alumnoRepository,
                                             ETSRepository etsRepository,
                                             InscripcionETSRepository inscripcionETSRepository) {
        return args -> {
            System.out.println("=========== CREACIÓN DE INSCRIPCIONES (ORDER13) ==============");
            Alumno alfredo = alumnoRepository.findByBoleta("2022630467").orElse(null);

            if (alfredo == null) {
                System.out.println("=========== NO HAY ALUMNOS ==============");
            }

            ETS ets1 = etsRepository.findById(1).orElse(null);
            ETS ets2 = etsRepository.findById(2).orElse(null);
            ETS ets3 = etsRepository.findById(3).orElse(null);

            System.out.println("=========== PRIMER REGISTRO ==============");
            InscripcionETSPK ietspk1 = new InscripcionETSPK();
            assert ets1 != null;
            ietspk1.setIdETS(ets1.getIdETS());
            assert alfredo != null;
            ietspk1.setBoleta(alfredo.getBoleta());

            InscripcionETS ins1 = new InscripcionETS();
            ins1.setId(ietspk1);
            ins1.setAlumno(alfredo);
            ins1.setEts(ets1);

            inscripcionETSRepository.save(ins1);

            System.out.println("=========== SEGUNDO REGISTRO ==============");
            InscripcionETSPK ietspk2 = new InscripcionETSPK();
            assert ets2 != null;
            ietspk2.setIdETS(ets2.getIdETS());
            ietspk2.setBoleta(alfredo.getBoleta());

            InscripcionETS ins2 = new InscripcionETS();
            ins2.setId(ietspk2);
            ins2.setAlumno(alfredo);
            ins2.setEts(ets2);

            inscripcionETSRepository.save(ins2);

            System.out.println("=========== TERCER REGISTRO ==============");
            InscripcionETSPK ietspk3 = new InscripcionETSPK();
            assert ets3 != null;
            ietspk3.setIdETS(ets3.getIdETS());
            ietspk3.setBoleta(alfredo.getBoleta());

            InscripcionETS ins3 = new InscripcionETS();
            ins3.setId(ietspk3);
            ins3.setAlumno(alfredo);
            ins3.setEts(ets3);

            inscripcionETSRepository.save(ins3);

        };
    }
}
