package com.example.PruebaCRUD.BD.Repositories;


import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadAcademicaRepository extends JpaRepository<UnidadAcademica, String> {
    Optional<UnidadAcademica> findByNombre (String nombre);
}
