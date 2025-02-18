package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.Repositories.TipoUsuarioRepository;
import com.example.PruebaCRUD.BD.TipoUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class TipoUsuarioConfig {
    /**
     *
     * @param tipoUsuarioRepository Repositorio Spring de TipoUsuario para manipular dicha tabla en la BD
     *
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(6) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataTipoU(TipoUsuarioRepository tipoUsuarioRepository) {
        return args -> {
            if (tipoUsuarioRepository.count() == 0) { // Si no encunetra registros entra aquí
                // Guarda nuevos registros
                tipoUsuarioRepository.save(new TipoUsuario(1, "Alumno"));
                tipoUsuarioRepository.save(new TipoUsuario(2, "Personal Academico"));
                tipoUsuarioRepository.save(new TipoUsuario(3, "Personal Seguridad"));
            }
        };
    }
}
