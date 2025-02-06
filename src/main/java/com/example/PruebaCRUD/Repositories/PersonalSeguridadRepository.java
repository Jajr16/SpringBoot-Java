package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.PersonalSeguridadPK;
import com.example.PruebaCRUD.BD.PersonalSeguridad;
import com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalSeguridadRepository extends JpaRepository<PersonalSeguridad, PersonalSeguridadPK> {

    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.PersonalSeguridadDTOSaes(
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre,
                t.nombre,
                c.nombre
            ) FROM PersonalSeguridad ps
            INNER JOIN Turno t ON ps.Turno.idTurno = t.idTurno
            INNER JOIN Persona p ON ps.id.CURP.CURP = p.CURP
            INNER JOIN CargoPS c ON ps.Cargo.idCargo = c.idCargo
            """)
    List<PersonalSeguridadDTOSaes> findPersonalSeguridad();
}
