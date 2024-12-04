package com.example.PruebaCRUD.Unidad_Académica;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnidadAcademicaRepository extends JpaRepository<UnidadAcademica, String> {
    Optional<UnidadAcademica> findByNombre (String nombre);
}
