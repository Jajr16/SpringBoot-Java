package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.UnidadAprendizaje;
import com.example.PruebaCRUD.DTO.UnidadAprendizajeProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadAprendizajeRepository extends JpaRepository<UnidadAprendizaje, String> {
    List<UnidadAprendizajeProjectionSaes> findAllBy();
}
