package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByTipo(String Tipo);
}
