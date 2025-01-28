package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.CargoPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoPSRepository extends JpaRepository<CargoPS, Integer> {
    Optional<CargoPS> findByNombre(String Nombre);
}
