package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsuario(String usuario);

    @Query(value = "SELECT * FROM login(:username, :password)", nativeQuery = true)
    Object callLoginFunction(@Param("username") String username, @Param("password") String password);
}
