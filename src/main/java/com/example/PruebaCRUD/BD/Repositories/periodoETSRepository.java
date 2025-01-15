package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.periodoETS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface periodoETSRepository extends JpaRepository<periodoETS, String> {
    Optional<periodoETS> findByPeriodoAndTipo(String periodo, char tipo);

    List<periodoETS> findByPeriodo(String periodo);
}
