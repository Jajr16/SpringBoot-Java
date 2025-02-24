package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.PersonalSeguridadPK;
import com.example.PruebaCRUD.BD.PersonalSeguridad;
import com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface PersonalSeguridadRepository extends JpaRepository<PersonalSeguridad, PersonalSeguridadPK> {

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes(
                CONCAT(p.nombre, " ", p.apellido_p, " ", p.apellido_m) as nombre,
                t.nombre,
                c.nombre
            ) FROM PersonalSeguridad ps
            INNER JOIN Turno t ON ps.Turno.idTurno = t.idTurno
            INNER JOIN Persona p ON ps.id.CURP.CURP = p.CURP
            INNER JOIN CargoPS c ON ps.Cargo.idCargo = c.idCargo
            """)
    List<PersonalSeguridadDTOSaes> findPersonalSeguridad();
}
