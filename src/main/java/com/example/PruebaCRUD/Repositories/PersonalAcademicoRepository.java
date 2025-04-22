package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Persona;
import com.example.PruebaCRUD.BD.PersonalAcademico;
import com.example.PruebaCRUD.DTO.DocenteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que funcionará como la capa de persistencia entre el sistema y la base de datos.
 * Extiende de JpaRepository (ayuda a gestionar los datos de una BD)
 */
@Repository
public interface PersonalAcademicoRepository extends JpaRepository<PersonalAcademico, String> {
    // Notación findBy(Columna con primera mayúscula) proporcionada por JPA
    Optional<PersonalAcademico> findByRfc(String RFC);

    @Query("SELECT new com.example.PruebaCRUD.DTO.DocenteDTO(pa.rfc, CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m)) " +
            "FROM PersonalAcademico pa JOIN pa.CURP p")
    List<DocenteDTO> findDocentes();

}
