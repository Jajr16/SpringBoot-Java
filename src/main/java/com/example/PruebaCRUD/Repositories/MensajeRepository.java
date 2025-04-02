package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Mensaje;
import com.example.PruebaCRUD.BD.PKCompuesta.MensajePK;
import com.example.PruebaCRUD.BD.Usuario;
import com.example.PruebaCRUD.DTO.ChatsDTO;
import com.example.PruebaCRUD.DTO.ListadoUsuariosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
                CONCAT(p.nombre, " ", p.apellido_p, " ", p.apellido_m) as nombre,
                tu.tipo
            ) FROM Usuario u
            INNER JOIN Persona p ON u.CURP.CURP = p.CURP
            INNER JOIN TipoUsuario tu ON u.TipoU.idTU = tu.idTU
            WHERE tu.tipo != 'Personal Seguridad'
            """)
    List<ListadoUsuariosDTO> findUsers();

}
