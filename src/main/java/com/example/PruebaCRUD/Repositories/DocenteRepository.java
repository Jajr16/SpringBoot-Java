package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOToETS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface DocenteRepository extends JpaRepository<PersonalAcademico, String> {
    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes(
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre,
                pera.correoi
                ) FROM PersonalAcademico as pera
            INNER JOIN Persona p ON pera.CURP.CURP = p.CURP
            INNER JOIN CargoDocente cd ON cd.RFCCD.RFC = pera.RFC
            INNER JOIN Cargo c ON c.id_cargo = cd.idCargoCD.id_cargo
            WHERE pera.TipoPA.Cargo = 'Docente'
            """)
    List<DocentesDTOSaes> findDocentes();

    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.DocentesDTOToETS(
                p.CURP,
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre
                ) FROM PersonalAcademico as pera
            INNER JOIN Persona p ON pera.CURP.CURP = p.CURP
            INNER JOIN CargoDocente cd ON cd.RFCCD.RFC = pera.RFC
            INNER JOIN Cargo c ON c.id_cargo = cd.idCargoCD.id_cargo
            WHERE pera.TipoPA.Cargo = 'Docente'
            """)
    List<DocentesDTOToETS> findDocentesToSaes();

    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<PersonalAcademico> findByCURP(Persona persona);
}
