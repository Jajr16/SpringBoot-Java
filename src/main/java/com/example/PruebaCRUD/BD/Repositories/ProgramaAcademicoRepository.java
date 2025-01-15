package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, String> {
    Optional<ProgramaAcademico> findByIdPA(String Nombre);
}
