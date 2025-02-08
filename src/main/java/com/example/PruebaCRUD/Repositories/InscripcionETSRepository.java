package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
public interface InscripcionETSRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query(value = "SELECT * FROM ListInscripcionesETS(:boleta)", nativeQuery = true)
    List<Object[]> callListInscripcionesETS(@Param("boleta") String boleta);

    // Notación existsBy(Columna con primera mayúscula) proporcionada por JPA
    boolean existsByBoletaInsBoleta(@Param("boleta") String boleta);
}
