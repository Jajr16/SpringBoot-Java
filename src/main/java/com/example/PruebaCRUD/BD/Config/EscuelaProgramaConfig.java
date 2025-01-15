package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import com.example.PruebaCRUD.BD.PKCompuesta.EscuelaProgramaPK;
import com.example.PruebaCRUD.BD.Repositories.EscuelaProgramaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class EscuelaProgramaConfig {

    @Bean
    @Order(3)
    CommandLineRunner initDataEP(EscuelaProgramaRepository escuelaProgramaRepository) {
        return args -> {
            if(escuelaProgramaRepository.count() == 0) {
                EscuelaProgramaPK reg1 = new EscuelaProgramaPK();
                reg1.setIdEscuela(1);
                reg1.setIdPA("ISC-2024");

                EscuelaPrograma escuelaProgramareg1 = new EscuelaPrograma();
                escuelaProgramareg1.setId(reg1);

                escuelaProgramaRepository.save(escuelaProgramareg1);

                EscuelaProgramaPK reg2 = new EscuelaProgramaPK();
                reg2.setIdEscuela(2);
                reg2.setIdPA("IIA-2024");

                EscuelaPrograma escuelaProgramareg2 = new EscuelaPrograma();
                escuelaProgramareg2.setId(reg2);

                escuelaProgramaRepository.save(escuelaProgramareg2);

                EscuelaProgramaPK reg3 = new EscuelaProgramaPK();
                reg3.setIdEscuela(3);
                reg3.setIdPA("ICD-2024");

                EscuelaPrograma escuelaProgramareg3 = new EscuelaPrograma();
                escuelaProgramareg3.setId(reg3);

                escuelaProgramaRepository.save(escuelaProgramareg3);
            }
        };
    }
}
