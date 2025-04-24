package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Chat;
import com.example.PruebaCRUD.DTO.ChatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("""
        SELECT new com.example.PruebaCRUD.DTO.ChatsDTO(
            c.destinatario.usuario,
            CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m)
        ) FROM Chat c 
        INNER JOIN c.destinatario.CURP p 
        WHERE c.remitente.usuario = :remitente
    """)
    List<ChatsDTO> findChatsRemitente(String remitente);

    @Query("""
        SELECT c FROM Chat c 
        WHERE (c.remitente.usuario = :remitente AND c.destinatario.usuario = :destinatario) 
        OR (c.remitente.usuario = :destinatario AND c.destinatario.usuario = :remitente)
    """)
    Optional<Chat> findChatByUsers(@Param("remitente") String remitente, @Param("destinatario") String destinatario);

    @Query("""
        SELECT new com.example.PruebaCRUD.DTO.ChatsDTO(
            c.remitente.usuario,
            CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m)
        ) FROM Chat c 
        INNER JOIN c.remitente.CURP p 
        WHERE c.destinatario.usuario = :destinatario
    """)
    List<ChatsDTO> findChatsDestinatario(String destinatario);

    Optional<Chat> findByRemitente_UsuarioAndDestinatario_Usuario(String remitente, String destinatario);
    Optional<Chat> findByDestinatario_UsuarioAndRemitente_Usuario(String destinatario, String remitente);
}
