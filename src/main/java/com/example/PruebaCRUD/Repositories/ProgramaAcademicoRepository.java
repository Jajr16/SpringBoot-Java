package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, String> {
    Optional<ProgramaAcademico> findByIdPA(String Nombre);
}
