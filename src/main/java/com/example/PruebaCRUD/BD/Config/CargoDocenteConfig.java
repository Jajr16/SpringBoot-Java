//package com.example.PruebaCRUD.BD.Config;
//
//import com.example.PruebaCRUD.BD.Cargo;
//import com.example.PruebaCRUD.BD.CargoDocente;
//import com.example.PruebaCRUD.BD.PKCompuesta.CargoDocentePK;
//import com.example.PruebaCRUD.BD.PersonalAcademico;
//import com.example.PruebaCRUD.Repositories.CargoDocenteRepository;
//import com.example.PruebaCRUD.Repositories.CargoRepository;
//import com.example.PruebaCRUD.Repositories.PersonalAcademicoRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
///**
// * Archivo de configuración con la función de prellenar las tablas de la base de datos. Esta tabla se trata de una
// * tabla que es la relación M:N de otras dos tablas.
// */
//@Configuration // Indica que la clase es de configuración (en este caso para prellenar una tabla de la BD)
//public class CargoDocenteConfig {
//    /**
//     *
//     * @param cargoDocenteRepository Reporitorio Spring de CargoDocente el cual servirá para manipular dicha tabla en la BD
//     * @param personalAcademicoRepository Reporitorio Spring del PersonalAcademico el cual servirá para manipular dicha
//     *                                    tabla en la BD
//     * @param cargoRepository Reporitorio Spring de Cargo el cual servirá para manipular dicha tabla en la BD
//     *
//     */
//    @Bean // Define objetos administrados por Spring e indica que retornará dicho objeto
//    @Order(20) // Orden en el que se ejecutará este fragmento de código
//    CommandLineRunner initDataCargosDocente(CargoDocenteRepository cargoDocenteRepository,
//                                            PersonalAcademicoRepository personalAcademicoRepository,
//                                            CargoRepository cargoRepository) {
//        return args -> {
//            if (cargoDocenteRepository.count() == 0) {  // Si aún no hay ningún registro en la tabla cargodocente entra aquí
//                // Busca el registro de cada personal academico por RFC, en caso de no encontrarlo devuelve null
//                PersonalAcademico Ulises = personalAcademicoRepository.findByRfc("D").orElse(null);
//                PersonalAcademico Saul = personalAcademicoRepository.findByRfc("E").orElse(null);
//                PersonalAcademico Chadwick = personalAcademicoRepository.findByRfc("F").orElse(null);
//                PersonalAcademico Andres = personalAcademicoRepository.findByRfc("G").orElse(null);
//
//                // Busca el registro de cada Cargo del personal academico, en caso de no encontrarlo devuelve null
//                Cargo Docente = cargoRepository.findByCargo("Docente").orElse(null);
//                Cargo Subdirector = cargoRepository.findByCargo("Subdirector").orElse(null);
//                Cargo Director = cargoRepository.findByCargo("Director").orElse(null);
//
//                // PRIMER REGISTRO
//                /**
//                 * Para llevar a cabo un regsitro de una tabla con llaves foráneas y llave primaria compuesta
//                 * debes de llenar todas las variables que contiene la clase princiapl
//                 * CargoDocente, estos incluyen:
//                 *      - CargoDocentePK (Instancia de otra clase)
//                 *      - RFC (Instancia de otra clase)
//                 *      - Cargo (Instancia de otra clase)
//                 */
//                // Instancia de la llave primaria de CargoDocente (Aquí se deben de llenar las variables normales)
//                CargoDocentePK pkU = new CargoDocentePK();
//                assert Ulises != null; // Afirma que la variable Ulises no sea null
//                pkU.setRFC(Ulises.getRFC()); // Se asigna el RFC
//                assert Docente != null; // Afirma que la variable Docente no sea null
//                pkU.setIdCargo(Docente.getIdCargo()); // Se asigna el cargo
//
//                /**
//                 * Se hace la instancia de CargoDocente (tabla princiapl), como se mencionó, se deben de asignar todas
//                 * las variables de dicha clase, las cuales son instancias de otras clases.
//                 */
//                CargoDocente cdU = new CargoDocente();
//                cdU.setId(pkU);
//                cdU.setRFC(Ulises);
//                cdU.setIdCargo(Docente);
//
//                // SEGUNDO REGISTRO
//                CargoDocentePK pkS = new CargoDocentePK();
//                assert Saul != null;
//                pkS.setRFC(Saul.getRFC());
//                pkS.setIdCargo(Docente.getIdCargo());
//
//                CargoDocente cdS = new CargoDocente();
//                cdS.setId(pkS);
//                cdS.setRFC(Saul);
//                cdS.setIdCargo(Docente);
//
//                // TERCER REGISTRO
//                CargoDocentePK pkC = new CargoDocentePK();
//                assert Chadwick != null;
//                pkC.setRFC(Chadwick.getRFC());
//                pkC.setIdCargo(Docente.getIdCargo());
//
//                CargoDocente cdC = new CargoDocente();
//                cdC.setId(pkC);
//                cdC.setRFC(Chadwick);
//                cdC.setIdCargo(Docente);
//
//                // CUARTO REGISTRO
//                CargoDocentePK pkA = new CargoDocentePK();
//                assert Andres != null;
//                pkA.setRFC(Andres.getRFC());
//                pkA.setIdCargo(Docente.getIdCargo());
//
//                CargoDocente cdA = new CargoDocente();
//                cdA.setId(pkA);
//                cdA.setRFC(Andres);
//                cdA.setIdCargo(Docente);
//
//                // Se guardan todos los registros anteriores.
//                cargoDocenteRepository.save(cdU);
//                cargoDocenteRepository.save(cdS);
//                cargoDocenteRepository.save(cdC);
//                cargoDocenteRepository.save(cdA);
//            }
//        };
//    }
//}
