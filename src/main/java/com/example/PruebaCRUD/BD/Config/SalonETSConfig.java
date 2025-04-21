//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.ETS;
//import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
//import com.example.PruebaCRUD.BD.Salon;
//import com.example.PruebaCRUD.BD.SalonETS;
//import com.example.PruebaCRUD.Repositories.ETSRepository;
//import com.example.PruebaCRUD.Repositories.SalonETSRepository;
//import com.example.PruebaCRUD.Repositories.SalonRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
///**
// * Archivo de configuración con la función de prellenar las tablas de la base de datos
// */
//@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
//public class SalonETSConfig {
//    /**
//     *
//     * @param salonRepository Repositorio Spring de Salon para manipular dicha tabla en la BD
//     * @param etsRepository Repositorio Spring de ETS para manipular dicha tabla en la BD
//     * @param salonETSRepository Repositorio Spring de SalonETS para manipular dicha tabla en la BD
//     *
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(16) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataSalonETS(SalonRepository salonRepository, ETSRepository etsRepository,
//                                       SalonETSRepository salonETSRepository) {
//        return args -> {
//            //  Busca registros por NumSalon, en caso de no encontrarlo devuelve null
//            Salon S4108 = salonRepository.findByNumSalon(4108).orElse(null);
//            Salon S3210 = salonRepository.findByNumSalon(3210).orElse(null);
//            Salon S1111 = salonRepository.findByNumSalon(1111).orElse(null);
//
//            //  Busca registros por ID, en caso de no encontrarlo devuelve null
//            ETS ets1 = etsRepository.findById(1).orElse(null);
//            ETS ets2 = etsRepository.findById(2).orElse(null);
//            ETS ets3 = etsRepository.findById(3).orElse(null);
//
//            if (salonETSRepository.count() == 0) { // Si no encuentra registros entra aquí
//                /**
//                 * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
//                 * debes de llenar todas las variables que contiene la clase princiapl
//                 * SalonETS, estos incluyen:
//                 *      - SalonETSPK (Instancia de otra clase)
//                 *      - Salon (Instancia de otra clase)
//                 *      - ETS (Instancia de otra clase)
//                 */
//                // Instancia de la llave primaria de SalonETS (Aquí se deben de llenar las variables normales)
//                SalonETSPK  sets1 = new SalonETSPK();
//                assert S4108 != null; // Afirma que la variable no sea null
//                sets1.setNumSalon(S4108.getNumSalon()); // Asigna la variable numSalon
//                assert ets1 != null; // Afirma que la variable no sea null
//                sets1.setIdETS(ets1.getIdETS()); // Asigna la variable IdETS
//
//                /**
//                 * Se hace la instancia de SalonETS (tabla princiapl), como se mencionó, se deben de asignar todas
//                 * las variables de dicha clase, las cuales son instancias de otras clases.
//                 */
//                SalonETS salon1 = new SalonETS();
//                salon1.setId(sets1); // Asigna la llave primaria compuesta de SalonETS
//                salon1.setNumSalon(S4108); // Asigna el Salon
//                salon1.setIdETS(ets1); // Asigna el ETS
//                // Guarda el registro anterior
//                salonETSRepository.save(salon1);
//
//                // SEGUNDO REGISTRO
//                SalonETSPK sets2 = new SalonETSPK();
//                assert S3210 != null;
//                sets2.setNumSalon(S3210.getNumSalon());
//                assert ets2 != null;
//                sets2.setIdETS(ets2.getIdETS());
//
//                SalonETS salon2 = new SalonETS();
//                salon2.setId(sets2);
//                salon2.setNumSalon(S3210);
//                salon2.setIdETS(ets2);
//
//                salonETSRepository.save(salon2);
//
//                // TERCER REGISTRO
//                SalonETSPK sets3 = new SalonETSPK();
//                assert S1111 != null;
//                sets3.setNumSalon(S1111.getNumSalon());
//                assert ets3 != null;
//                sets3.setIdETS(ets3.getIdETS());
//
//                SalonETS salon3 = new SalonETS();
//                salon3.setId(sets3);
//                salon3.setNumSalon(S1111);
//                salon3.setIdETS(ets3);
//
//                salonETSRepository.save(salon3);
//            }
//
//
//        };
//    }
//}
