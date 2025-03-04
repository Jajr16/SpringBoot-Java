package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.BD.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaDataRepository extends JpaRepository<Persona, Alumno> {

    @Query(value = "SELECT * FROM obtenerpersona(:usuario)", nativeQuery = true)
    List<Object[]> callobtenerpersona(@Param("usuario") String usuario);


}
