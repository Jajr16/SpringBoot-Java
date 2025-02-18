package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
public interface TurnoRepository extends JpaRepository<Turno, Integer> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<Turno> findByNombre(String Nombre);
}
