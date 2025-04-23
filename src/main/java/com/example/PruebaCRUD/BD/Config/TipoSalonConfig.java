//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.TipoSalon;
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
//public class TipoSalonConfig {
//    /**
//     *
//     * @param tipoSalonRepository Repositorio Spring de TipoSalon para manipular dicha tabla en la BD
//     *
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(14) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataTipoSalon(TipoSalonRepository tipoSalonRepository) {
//        return args -> {
//            if (tipoSalonRepository.count() == 0) { // Si no encuentra registros entra aquí
//                // Guarda nuevos registros
//                tipoSalonRepository.save(new TipoSalon(1, "Laboratorio"));
//                tipoSalonRepository.save(new TipoSalon(2, "Normal"));
//            }
//        };
//    }
//}
