package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PersonalAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalAcademicoRepository extends JpaRepository<PersonalAcademico, String> {
    Optional<PersonalAcademico> findByRFC(String RFC);
}
