package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Usuario;
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
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<Usuario> findByUsuario(String usuario);

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query(value = "SELECT * FROM login(:username, :password)", nativeQuery = true)
    List<Object[]> callLoginFunction(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT p.id_escuela FROM Persona p INNER JOIN Usuario u ON p.curp = u.curp" +
            " WHERE u.usuario = (:user)", nativeQuery = true)
    Integer findEscuela(@Param("user") String user);
}
