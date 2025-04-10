package com.example.PruebaCRUD.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.PruebaCRUD.BD.Sexo;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface SexoRepository extends JpaRepository<Sexo, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<Sexo> findByNombre(String nombre);
}
