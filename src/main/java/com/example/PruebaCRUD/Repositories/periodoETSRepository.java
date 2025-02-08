package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.Saes.PeriodosETSProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface periodoETSRepository extends JpaRepository<periodoETS, Integer> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<periodoETS> findByPeriodoAndTipo(String periodo, char tipo);

    List<periodoETS> findByPeriodo(String periodo);

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query(value = "SELECT pe.fecha_inicio FROM periodoets pe WHERE periodo = :periodo LIMIT 1", nativeQuery = true)
    String findFechaByPeriodo(String periodo);

    List<PeriodosETSProjectionSaes> findAllBy();
}
