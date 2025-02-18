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

/**
 * Archivo de configuración con la función de prellenar las tablas de la base de datos
 */
@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
public class PersonalSeguridadConfig {
    /**
     *
     * @param personalSeguridadRepository Repositorio Spring de PersonalSeguridad para manipular dicha tabla en la BD
     * @param cargoPSRepository Repositorio Spring de CargoPS para manipular dicha tabla en la BD
     * @param turnoRepository Repositorio Spring de Turno para manipular dicha tabla en la BD
     * @param personaRepository Repositorio Spring de Persona para manipular dicha tabla en la BD
     * @return
     */
    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
    @Order(22) // Orden en el que se ejecutará este fragmento de código
    CommandLineRunner initDataPSConfig(PersonalSeguridadRepository personalSeguridadRepository,
                                       CargoPSRepository cargoPSRepository,
                                       TurnoRepository turnoRepository,
                                       PersonaRepository personaRepository) {
        return args -> {
            if (personalSeguridadRepository.count() == 0) { // Si no encuentra registros entra aquí

                // Busca registros de Persona por CURP, en caso de no encontrarlo devuelve null
                Persona Esteban = personaRepository.findPersonaByCURP("7").orElse(null);
                Persona Flor = personaRepository.findPersonaByCURP("8").orElse(null);
                Persona Carlos = personaRepository.findPersonaByCURP("9").orElse(null);

                // Busca registros de Turno por Nombre, en caso de no encontrarlo devuelve null
                Turno matutino = turnoRepository.findByNombre("Matutino").orElse(null);
                Turno vespertino = turnoRepository.findByNombre("Vespertino").orElse(null);

                // Busca registros de CargoPS por Nombre, en caso de no encontrarlo crea un nuevo registro
                CargoPS Jefe = cargoPSRepository.findByNombre("Jefe").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Jefe"))
                );
                CargoPS SubJefe = cargoPSRepository.findByNombre("Subjefe").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Subjefe"))
                );
                CargoPS Guardia = cargoPSRepository.findByNombre("Guardia").orElseGet(() ->
                    cargoPSRepository.save(new CargoPS("Guardia"))
                );

                /**
                 * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
                 * debes de llenar todas las variables que contiene la clase princiapl
                 * PersonalSeguridad, estos incluyen:
                 *      - PersonalSeguridadPK (Instancia de otra clase)
                 *      - Turno (Instancia de otra clase)
                 *      - CargoPS (Instancia de otra clase)
                 */
                // Instancia de la llave primaria de PersonalSeguridad (Aquí se deben de llenar las variables normales)
                PersonalSeguridadPK pspkE = new PersonalSeguridadPK();
                pspkE.setCURP(Esteban); // Se asigna el CURP

                /**
                 * Se hace la instancia de CargoDocente (tabla princiapl), como se mencionó, se deben de asignar todas
                 * las variables de dicha clase, las cuales son instancias de otras clases.
                 */
                PersonalSeguridad psE = new PersonalSeguridad();
                psE.setId(pspkE); // Se asigna la llave primaria compuesta
                psE.setTurno(matutino); // Se asigna el turno
                psE.setCargo(Jefe); // Se asigna el cargo

                // SEGUNDO REGISTRO
                PersonalSeguridadPK pspkF = new PersonalSeguridadPK();
                pspkF.setCURP(Flor);

                PersonalSeguridad psF = new PersonalSeguridad();
                psF.setId(pspkF);
                psF.setTurno(vespertino);
                psF.setCargo(SubJefe);

                // TERCER REGISTRO
                PersonalSeguridadPK pspkC = new PersonalSeguridadPK();
                pspkC.setCURP(Carlos);

                PersonalSeguridad psC = new PersonalSeguridad();
                psC.setId(pspkC);
                psC.setTurno(matutino);
                psC.setCargo(Guardia);

                // Guarda todos los registros anteriores
                personalSeguridadRepository.save(psE);
                personalSeguridadRepository.save(psF);
                personalSeguridadRepository.save(psC);
            }
        };
    }
}
