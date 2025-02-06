package com.example.PruebaCRUD.Repositories;


import com.example.PruebaCRUD.BD.UnidadAcademica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadAcademicaRepository extends JpaRepository<UnidadAcademica, String> {
    Optional<UnidadAcademica> findByNombre (String nombre);
}
