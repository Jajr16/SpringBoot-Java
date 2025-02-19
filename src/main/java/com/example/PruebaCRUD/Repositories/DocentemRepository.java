package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PersonalAcademico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocentemRepository extends JpaRepository<PersonalAcademico, String> {

    Optional<PersonalAcademico> findByRfc(String s);
}
