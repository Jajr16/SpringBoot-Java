package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface MensajeRepositorio extends JpaRepository<Mensaje, MensajePK> {
    // Obtener los mensajes de un chat específico ordenados por fecha
    List<Mensaje> findById_Chat_IdOrderById_FechahoraAsc(Long chatId);
}
