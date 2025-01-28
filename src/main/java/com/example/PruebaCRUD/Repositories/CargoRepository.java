package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    Optional<Cargo> findByCargo(String cargo);
}
