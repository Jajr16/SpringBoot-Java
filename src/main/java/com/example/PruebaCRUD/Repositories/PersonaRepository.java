package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.DTO.PersonaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface PersonaRepository extends JpaRepository<Persona, String> {

    // Lo de abajo es lo mismo que hacer esto: @Query("SELECT * FROM Persona WHERE CURP = ?")
    Optional<Persona> findPersonaByCURP(String curp);

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.PersonaDTO(
                CONCAT(p.nombre, " ", p.apellido_p, " ", p.apellido_m) as nombre,
                p.sexo.nombre,
                p.unidadAcademica.nombre
            ) FROM Persona as p
            """)
    List<PersonaDTO> findAllAsDTO();

}
