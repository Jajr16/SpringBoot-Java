package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<ProgramaAcademico> findByIdPA(String Nombre);

    @Query(value = """
            SELECT pa.* FROM programaacademico pa INNER JOIN escuelaprograma ep ON ep.idpa = pa.idpa
            WHERE ep.id_escuela = (:escuela) AND ep.idpa = (:programaacademico)
            """, nativeQuery = true)
    Optional<ProgramaAcademico>findBy(@Param("escuela") Integer escuela, @Param("programaacademico") String pa);
}
