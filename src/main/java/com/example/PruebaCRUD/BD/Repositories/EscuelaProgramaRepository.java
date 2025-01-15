package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.EscuelaPrograma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EscuelaProgramaRepository extends JpaRepository<EscuelaPrograma, String> {
    Optional<EscuelaPrograma> findBy
}
