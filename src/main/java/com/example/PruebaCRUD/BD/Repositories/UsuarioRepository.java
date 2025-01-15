package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsuario(String usuario);
}
