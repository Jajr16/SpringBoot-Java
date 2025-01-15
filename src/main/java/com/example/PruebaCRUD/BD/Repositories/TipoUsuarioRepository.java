package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, String> {
    Optional<TipoUsuario> findByTipo(String Tipo);
}
