package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SexoRepository extends JpaRepository<Sexo, String> {
    Optional<Sexo> findByNombre(String nombre);
}
