package com.example.PruebaCRUD.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PruebaCRUD.BD.Sexo;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, String> {
    Optional<Sexo> findByNombre(String nombre);
}
