package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.periodoETS;
import com.example.PruebaCRUD.DTO.TimeToETSDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface periodoETSRepository extends JpaRepository<periodoETS, String> {

    Optional<periodoETS> findByPeriodoAndTipo(String periodo, char tipo);

    List<periodoETS> findByPeriodo(String periodo);

    @Query(value = "SELECT pe.fecha_inicio FROM periodoets pe WHERE periodo = :periodo LIMIT 1", nativeQuery = true)
    String findFechaByPeriodo(String periodo);
}
