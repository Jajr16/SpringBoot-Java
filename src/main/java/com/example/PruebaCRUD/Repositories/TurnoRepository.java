package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    Optional<Turno> findByNombre(String Nombre);
}
