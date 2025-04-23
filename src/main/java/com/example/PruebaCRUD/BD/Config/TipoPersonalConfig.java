//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.TipoPersonal;
//import com.example.PruebaCRUD.Repositories.TipoPersonalRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
///**
// * Archivo de configuración con la función de prellenar las tablas de la base de datos
// */
//@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
//public class TipoPersonalConfig {
//    /**
//     *
//     * @param tipoPersonalRepository Repositorio Spring de TipoPersonal para manipular dicha tabla en la BD
//     *
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(17) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataTP(TipoPersonalRepository tipoPersonalRepository) {
//        return args -> {
//            if (tipoPersonalRepository.count() == 0) { // Si no encuentra registros entra aquí
//                // Guarda nuevos registros
//                tipoPersonalRepository.save(new TipoPersonal(1, "Docente"));
//                tipoPersonalRepository.save(new TipoPersonal(2, "Jefe Gestión Escolar"));
//                tipoPersonalRepository.save(new TipoPersonal(3, "Personal Gestión Escolar"));
//                tipoPersonalRepository.save(new TipoPersonal(4, "Jefe Departamento"));
//            }
//        };
//    }
//}
