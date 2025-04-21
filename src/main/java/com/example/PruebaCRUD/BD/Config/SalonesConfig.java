//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.Salon;
//import com.example.PruebaCRUD.BD.TipoSalon;
//import com.example.PruebaCRUD.Repositories.SalonRepository;
//import com.example.PruebaCRUD.Repositories.TipoSalonRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
///**
// * Archivo de configuración con la función de prellenar las tablas de la base de datos
// */
//@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
//public class SalonesConfig {
//    /**
//     *
//     * @param salonRepository Repositorio Spring de Salon para manipular dicha tabla en la BD
//     * @param tipoSalonRepository Repositorio Spring de TipoSalon para manipular dicha tabla en la BD
//     * @return
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(15) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataSalones(SalonRepository salonRepository,
//                                      TipoSalonRepository tipoSalonRepository) {
//        return args -> {
//            if (salonRepository.count() == 0 ) { // Si no encuentra registros entra aquí
//                // Busca registros por Tipo, en caso de no encontrarlo devuelve null
//                TipoSalon lab = tipoSalonRepository.findByTipo("Laboratorio").orElse(null);
//                TipoSalon normal = tipoSalonRepository.findByTipo("Normal").orElse(null);
//
//                // Guarda nuevos registros con los datos anteriores
//                salonRepository.save(new Salon(4108, 4, 1, lab));
//                salonRepository.save(new Salon(3210, 3, 2, normal));
//                salonRepository.save(new Salon(1111, 1, 1, normal));
//            }
//        };
//    }
//}
