package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.Cargo;
import com.example.PruebaCRUD.BD.CargoDocente;
import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.Repositories.CargoDocenteRepository;
import com.example.PruebaCRUD.Repositories.CargoRepository;
import com.example.PruebaCRUD.Repositories.PersonalAcademicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class CargoDocenteConfig {
    @Bean
    @Order(20)
    CommandLineRunner initDataCargosDocente(CargoDocenteRepository cargoDocenteRepository,
                                            PersonalAcademicoRepository personalAcademicoRepository,
                                            CargoRepository cargoRepository) {
        return args -> {
            if (cargoDocenteRepository.count() == 0) {
                PersonalAcademico Ulises = personalAcademicoRepository.findByRFC("D").orElse(null);
                PersonalAcademico Saul = personalAcademicoRepository.findByRFC("E").orElse(null);
                PersonalAcademico Chadwick = personalAcademicoRepository.findByRFC("F").orElse(null);
                PersonalAcademico Andres = personalAcademicoRepository.findByRFC("G").orElse(null);

                Cargo Docente = cargoRepository.findByCargo("Docente").orElse(null);
                Cargo Subdirector = cargoRepository.findByCargo("Subdirector").orElse(null);
                Cargo Director = cargoRepository.findByCargo("Director").orElse(null);

//              ULISES
                CargoDocentePK pkU = new CargoDocentePK();
                assert Ulises != null;
                pkU.setRFC(Ulises.getRFC());
                assert Docente != null;
                pkU.setIdCargo(Docente.getIdCargo());

                CargoDocente cdU = new CargoDocente();
                cdU.setId(pkU);
                cdU.setRFC(Ulises);
                cdU.setIdCargo(Docente);

//                DE LA O
                CargoDocentePK pkS = new CargoDocentePK();
                assert Saul != null;
                pkS.setRFC(Saul.getRFC());
                pkS.setIdCargo(Docente.getIdCargo());

                CargoDocente cdS = new CargoDocente();
                cdS.setId(pkS);
                cdS.setRFC(Saul);
                cdS.setIdCargo(Docente);

//                CHADWICK
                CargoDocentePK pkC = new CargoDocentePK();
                assert Chadwick != null;
                pkC.setRFC(Chadwick.getRFC());
                pkC.setIdCargo(Docente.getIdCargo());

                CargoDocente cdC = new CargoDocente();
                cdC.setId(pkC);
                cdC.setRFC(Chadwick);
                cdC.setIdCargo(Docente);

//                ANDRES
                CargoDocentePK pkA = new CargoDocentePK();
                assert Andres != null;
                pkA.setRFC(Andres.getRFC());
                pkA.setIdCargo(Docente.getIdCargo());

                CargoDocente cdA = new CargoDocente();
                cdA.setId(pkA);
                cdA.setRFC(Andres);
                cdA.setIdCargo(Docente);

                cargoDocenteRepository.save(cdU);
                cargoDocenteRepository.save(cdS);
                cargoDocenteRepository.save(cdC);
                cargoDocenteRepository.save(cdA);
            }
        };
    }
}
