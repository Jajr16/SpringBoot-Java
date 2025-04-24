package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TokenNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface TokenNotificacionRepository extends JpaRepository<TokenNotificacion, Long> {
    Optional<TokenNotificacion> findByUsuarioUsuario(String usuarioId);
    Optional<TokenNotificacion> findByToken(String token);


    void deleteByUsuarioUsuario(String usuarioId);
}

