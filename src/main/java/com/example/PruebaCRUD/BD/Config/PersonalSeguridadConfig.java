package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.CargoPS;
import com.example.PruebaCRUD.BD.PKCompuesta.PersonalSeguridadPK;
import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalSeguridad;
import com.example.PruebaCRUD.BD.Turno;
import com.example.PruebaCRUD.Repositories.CargoPSRepository;
import com.example.PruebaCRUD.Repositories.PersonaRepository;
import com.example.PruebaCRUD.Repositories.PersonalSeguridadRepository;
import com.example.PruebaCRUD.Repositories.TurnoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class PersonalSeguridadConfig {
    @Bean
    @Order(22)
    CommandLineRunner initDataPSConfig(PersonalSeguridadRepository personalSeguridadRepository,
                                       CargoPSRepository cargoPSRepository,
                                       TurnoRepository turnoRepository,
                                       PersonaRepository personaRepository) {
        return args -> {
            if (personalSeguridadRepository.count() == 0) {

                Persona Esteban = personaRepository.findPersonaByCURP("7").orElse(null);
                Persona Flor = personaRepository.findPersonaByCURP("8").orElse(null);
                Persona Carlos = personaRepository.findPersonaByCURP("9").orElse(null);

                Turno matutino = turnoRepository.findByNombre("Matutino").orElse(null);
                Turno vespertino = turnoRepository.findByNombre("Matutino").orElse(null);

                CargoPS Jefe = cargoPSRepository.findByNombre("Jefe").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Jefe"))
                );
                CargoPS SubJefe = cargoPSRepository.findByNombre("Subjefe").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Subjefe"))
                );
                CargoPS Guardia = cargoPSRepository.findByNombre("Guardia").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Guardia"))
                );

                PersonalSeguridadPK pspkE = new PersonalSeguridadPK();
                pspkE.setCURP(Esteban);

                PersonalSeguridad psE = new PersonalSeguridad();
                psE.setId(pspkE);
                psE.setTurno(matutino);
                psE.setCargo(Jefe);

                PersonalSeguridadPK pspkF = new PersonalSeguridadPK();
                pspkF.setCURP(Flor);

                PersonalSeguridad psF = new PersonalSeguridad();
                psF.setId(pspkF);
                psF.setTurno(vespertino);
                psF.setCargo(SubJefe);

                PersonalSeguridadPK pspkC = new PersonalSeguridadPK();
                pspkC.setCURP(Carlos);

                PersonalSeguridad psC = new PersonalSeguridad();
                psC.setId(pspkC);
                psC.setTurno(matutino);
                psC.setCargo(Guardia);

                personalSeguridadRepository.save(psE);
                personalSeguridadRepository.save(psF);
                personalSeguridadRepository.save(psC);
            }
        };
    }
}
