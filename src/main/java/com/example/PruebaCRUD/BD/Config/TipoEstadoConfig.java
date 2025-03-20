package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.TipoEstado;
import com.example.PruebaCRUD.Repositories.TipoEstadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
@Configuration
public class TipoEstadoConfig {

    @Bean
<<<<<<< Updated upstream
    @Order(40)
=======
    @Order(1) // Ajusta el orden según sea necesario
>>>>>>> Stashed changes
    CommandLineRunner initDataTipoEstado(TipoEstadoRepository tipoEstadoRepository) {
        return args -> {
            System.out.println("=========== PRELLENANDO TABLA TIPOESTADO (ORDER1) ==============");
            if (tipoEstadoRepository.count() == 0) { // Verifica si la tabla está vacía
                // Guarda nuevos registros en la tabla tipoEstado
                tipoEstadoRepository.save(new TipoEstado(-1, "Rechazado: No se presentó."));
                tipoEstadoRepository.save(new TipoEstado(0, "Pendiente."));
                tipoEstadoRepository.save(new TipoEstado(1, "Rechazado: Verificado con el reconocimiento facial."));
                tipoEstadoRepository.save(new TipoEstado(2, "Rechazado: Verificado con el código QR de la credencial."));
                tipoEstadoRepository.save(new TipoEstado(3, "Rechazado: Verificado por el profesor."));
                tipoEstadoRepository.save(new TipoEstado(4, "Aceptado: Verificado con el reconocimiento facial."));
                tipoEstadoRepository.save(new TipoEstado(5, "Aceptado: Verificado con el código QR de la credencial."));
                tipoEstadoRepository.save(new TipoEstado(6, "Aceptado: Verificado por el profesor."));
            }
        };
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
