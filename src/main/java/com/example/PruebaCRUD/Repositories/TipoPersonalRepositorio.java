package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TipoPersonal;
import com.example.PruebaCRUD.DTO.Saes.TipoPersonalProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface TipoPersonalRepositorio extends JpaRepository<TipoPersonal, Integer> {
    // Notación findAllBy proporcionada por JPA
    List<TipoPersonalProjectionSaes> findAllBy();

    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<TipoPersonal> findByCargo(String Cargo);
}
