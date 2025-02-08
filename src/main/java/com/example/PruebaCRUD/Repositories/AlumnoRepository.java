
package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository // Notación para conseguir el patrón de la capa de persistencia de la BD
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
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
