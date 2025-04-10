package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsAlumnProjectionSaes;
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
                CONCAT(p.nombre, " ", p.apellido_p, " ", p.apellido_m) as nombre,
                a.boleta,
                pa.nombre,
                a.CorreoI
            ) FROM Alumno as a
            INNER JOIN Persona as p ON p.CURP = a.CURP.CURP
            INNER JOIN ProgramaAcademico as pa ON pa.idPA = a.idPA.idPA
            """)
    List<AlumnoDTOSaes> findAllAsDTO();

    @Query("""
            SELECT 
                a.boleta as boleta,
                CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m) as nombre
            FROM Persona p
            INNER JOIN Alumno a ON p.CURP = a.CURP.CURP
            WHERE p.unidadAcademica.id_Escuela = (SELECT p1.unidadAcademica.id_Escuela FROM Persona p1
                                            INNER JOIN Usuario u ON p1.CURP = u.CURP.CURP
                                            WHERE u.usuario = :usuario)
            """)
    List<ListInsAlumnProjectionSaes> findAlumnosSaes(String usuario);
}
