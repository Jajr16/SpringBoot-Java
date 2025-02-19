package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByBoleta(String Boleta);

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes(
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre,
                a.boleta,
                pa.nombre,
                a.CorreoI
            ) FROM Alumno as a
            INNER JOIN Persona as p ON p.CURP = a.CURP.CURP
            INNER JOIN ProgramaAcademico as pa ON pa.idPA = a.idPA.idPA
            """)
    List<AlumnoDTOSaes> findAllAsDTO();
}
