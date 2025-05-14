package com.example.PruebaCRUD.Repositories;

import java.util.Optional;

import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface GeneroRepositorio extends JpaRepository<Sexo, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<Sexo> findByNombre(String nombre);
}
