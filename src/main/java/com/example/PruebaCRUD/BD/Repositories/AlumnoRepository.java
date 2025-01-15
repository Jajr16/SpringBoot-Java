package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByBoleta(String Bolata);
}
