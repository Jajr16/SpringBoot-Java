package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ChatsDTO;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, MensajePK> {
    // Obtener los mensajes de un chat específico ordenados por fecha
    List<Mensaje> findById_Chat_IdOrderById_FechahoraAsc(Long chatId);

    @Query("""
    SELECT new com.example.PruebaCRUD.DTO.ListadoUsuariosDTO(
        u.usuario,
        CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m),
        tu.tipo
    ) FROM Usuario u
    INNER JOIN u.CURP p
    INNER JOIN u.TipoU tu
    WHERE p.unidadAcademica = (
        SELECT userP.unidadAcademica
        FROM Usuario searchUser
        INNER JOIN searchUser.CURP userP
        WHERE searchUser.usuario = :usuario
    )
    AND u.usuario != :usuario
    AND tu.tipo != 'Personal Seguridad'
    ORDER BY p.nombre
    """)
    List<ListadoUsuariosDTO> findUsers(@Param("usuario") String usuario);


}
