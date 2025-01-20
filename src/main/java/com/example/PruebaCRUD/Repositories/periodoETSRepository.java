package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.periodoETS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface periodoETSRepository extends JpaRepository<periodoETS, String> {
    Optional<periodoETS> findByPeriodoAndTipo(String periodo, char tipo);

    List<periodoETS> findByPeriodo(String periodo);
}
