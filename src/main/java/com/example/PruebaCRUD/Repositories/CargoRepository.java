package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Cargo;
import com.example.PruebaCRUD.DTO.Saes.CargoProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<Cargo> findByCargo(String cargo);

    List<CargoProjectionSaes> findAllBy();
}
