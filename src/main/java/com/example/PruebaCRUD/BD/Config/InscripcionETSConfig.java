//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.Alumno;
//import com.example.PruebaCRUD.BD.ETS;
//import com.example.PruebaCRUD.BD.InscripcionETS;
//import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
//import com.example.PruebaCRUD.Repositories.AlumnoRepository;
//import com.example.PruebaCRUD.Repositories.ETSRepository;
//import com.example.PruebaCRUD.Repositories.InscripcionETSRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
///**
// * Archivo de configuración con la función de prellenar las tablas de la base de datos
// */
//@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
//public class InscripcionETSConfig {
//    /**
//     *
//     * @param alumnoRepository Repositorio Spring de Alumno para manipular dicha tabla en la BD
//     * @param etsRepository Repositorio Spring de ETS para manipular dicha tabla en la BD
//     * @param inscripcionETSRepository Repositorio Spring de InscripcionETS para manipular dicha tabla en la BD
//     *
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(13) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataInscripcionETS(AlumnoRepository alumnoRepository,
//                                             ETSRepository etsRepository,
//                                             InscripcionETSRepository inscripcionETSRepository) {
//        return args -> {
//            // Busca registros de Alumno por Boleta, en caso de no encontrarlo devuelve null
//            Alumno alfredo = alumnoRepository.findByBoleta("2022630467").orElse(null);
//            Alumno alejandra = alumnoRepository.findByBoleta("2021340022").orElse(null);
//
//
//            // Busca registros de ETS por ID, en caso de no encontrarlo devuelve null
//            ETS ets1 = etsRepository.findById(1).orElse(null);
//            ETS ets2 = etsRepository.findById(2).orElse(null);
//            ETS ets3 = etsRepository.findById(3).orElse(null);
//
//            // PRIMER REGISTRO
//            /**
//             * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
//             * debes de llenar todas las variables que contiene la clase princiapl InscripcionETS, estos incluyen:
//             *      - InscripcionETSPK (Instancia de otra clase)
//             *      - Alumno (Instancia de otra clase)
//             *      - ETS (Instancia de otra clase)
//             */
//            // Instancia de la llave primaria de InscripcionETS (Aquí se deben de llenar las variables normales)
//            InscripcionETSPK ietspk1 = new InscripcionETSPK();
//            assert ets1 != null; // Afirma que la variable Ulises no sea null
//            ietspk1.setIdETS(ets1.getIdETS()); // Se asigna el ID del ETS
//            assert alfredo != null; // Afirma que la variable Ulises no sea null
//            ietspk1.setBoleta(alfredo.getBoleta()); // Se asigna la Boleta del Alumno
//
//            /**
//             * Se hace la instancia de InscripcionETS (tabla princiapl), como se mencionó, se deben de asignar todas
//             * las variables de dicha clase, las cuales son instancias de otras clases.
//             */
//            InscripcionETS ins1 = new InscripcionETS();
//            ins1.setId(ietspk1);
//            ins1.setAlumno(alfredo);
//            ins1.setEts(ets1);
//
//            // Se guarda el registro anterior
//            inscripcionETSRepository.save(ins1);
//
//            // SEGUNDO REGISTRO
//            InscripcionETSPK ietspk2 = new InscripcionETSPK();
//            assert ets2 != null;
//            ietspk2.setIdETS(ets2.getIdETS());
//            ietspk2.setBoleta(alfredo.getBoleta());
//
//            InscripcionETS ins2 = new InscripcionETS();
//            ins2.setId(ietspk2);
//            ins2.setAlumno(alfredo);
//            ins2.setEts(ets2);
//
//            inscripcionETSRepository.save(ins2);
//
//            // TERCER REGISTRO
////            InscripcionETSPK ietspk3 = new InscripcionETSPK();
////            assert ets3 != null;
////            ietspk3.setIdETS(ets3.getIdETS());
////            assert alejandra != null;
////            ietspk3.setBoleta(alejandra.getBoleta());
////
////            InscripcionETS ins3 = new InscripcionETS();
////            ins3.setId(ietspk3);
////            ins3.setAlumno(alejandra);
////            ins3.setEts(ets3);
////
////            inscripcionETSRepository.save(ins3);
//
//        };
//    }
//}
