package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SexoRepository extends JpaRepository<Sexo, String> {
    Optional<Sexo> findByNombre(String nombre);
}
