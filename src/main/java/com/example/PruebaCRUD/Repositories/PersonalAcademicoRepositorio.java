package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.DocentesDTOParaETS;
import com.example.PruebaCRUD.DTO.DocenteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface PersonalAcademicoRepositorio extends JpaRepository<PersonalAcademico, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<PersonalAcademico> findByRfc(String RFC);

    @Query("SELECT new com.example.PruebaCRUD.DTO.DocenteDTO(pa.rfc, CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m)) " +
            "FROM PersonalAcademico pa JOIN pa.CURP p")
    List<DocenteDTO> findDocentes();

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT DISTINCT new com.example.PruebaCRUD.DTO.Saes.DocentesDTOSaes(
                CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m) as nombre,
                pera.correoi
                ) FROM PersonalAcademico as pera
            INNER JOIN Persona p ON pera.CURP.CURP = p.CURP
            INNER JOIN CargoDocente cd ON cd.RFCCD.rfc = pera.rfc
            INNER JOIN Cargo c ON c.id_cargo = cd.idCargoCD.id_cargo
            WHERE pera.TipoPA.cargo = 'Docente'
            """)
    List<DocentesDTOSaes> findDocentesSaes();

    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.DocentesDTOParaETS(
                p.CURP,
                CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m) as nombre
                ) FROM PersonalAcademico as pera
            INNER JOIN Persona p ON pera.CURP.CURP = p.CURP
            INNER JOIN CargoDocente cd ON cd.RFCCD.rfc = pera.rfc
            INNER JOIN Cargo c ON c.id_cargo = cd.idCargoCD.id_cargo
            WHERE pera.TipoPA.cargo = 'Docente'
            """)
    List<DocentesDTOParaETS> findDocentesToSaes();

    @Query(value = """
            SELECT 1 FROM personalacademico pa INNER JOIN tipopersonal tp ON pa.tipopa = tp.tipopa WHERE
            pa.curp = (:curp) AND tp.cargo = 'Docente'
            """, nativeQuery = true)
    Optional<PersonalAcademico> existsByCURP(@Param("curp") String curp);

    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<PersonalAcademico> findByCURP(Persona persona);

}
