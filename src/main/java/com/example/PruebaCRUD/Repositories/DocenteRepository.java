package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.DTO.DocentesDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocenteRepository extends JpaRepository<PersonalAcademico, String> {
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.DocentesDTOSaes(
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre,
                pera.correoi
                ) FROM PersonalAcademico as pera
            INNER JOIN Persona p ON pera.CURP.CURP = p.CURP
            INNER JOIN CargoDocente cd ON cd.RFCCD.RFC = pera.RFC
            INNER JOIN Cargo c ON c.id_cargo = cd.idCargoCD.id_cargo
            WHERE pera.TipoPA.Cargo = 'Docente'
            """)
    List<DocentesDTOSaes> findDocentes();
}
