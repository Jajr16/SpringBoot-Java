package com.example.PruebaCRUD.BD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    // Lo de abajo es lo mismo que hacer esto: @Query("SELECT * FROM Persona WHERE CURP = ?")
    Optional<Persona> findPersonaByCURP(String curp);

}
