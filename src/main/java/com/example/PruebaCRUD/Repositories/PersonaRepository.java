package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.DTO.PersonaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    // Lo de abajo es lo mismo que hacer esto: @Query("SELECT * FROM Persona WHERE CURP = ?")
    Optional<Persona> findPersonaByCURP(String curp);

    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.PersonaDTO(
                CONCAT(p.Nombre, " ", p.Apellido_P, " ", p.Apellido_M) as nombre,
                p.sexo.nombre,
                p.unidadAcademica.nombre
            ) FROM Persona as p
            """)
    List<PersonaDTO> findAllAsDTO();

}
