package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Aplica;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface AplicaRepository extends JpaRepository<Aplica, AplicaPK> {

    @Query(value = "SELECT * FROM ListAplica(:docente_rfc)", nativeQuery = true)
    List<Object[]> callListAplica(@Param("docente_rfc") String docente_rfc);

    // Notación existsBy(Columna con primera mayúscula) proporcionada por JPA
    boolean existsByDocenteRFCRfc(@Param("docente_rfc") String docente_rfc);

    @Query(value = "SELECT obtener_docente_rfc(:idets)", nativeQuery = true)
    String callObtenerDocenteRfc(@Param("idets") int idets);

}
