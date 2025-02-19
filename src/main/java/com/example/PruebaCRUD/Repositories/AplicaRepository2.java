package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Aplica;
import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AplicaRepository2 extends JpaRepository<Aplica, AplicaPK> {

    @Query(value = "SELECT * FROM ListAplica(:docente_rfc)", nativeQuery = true)
    List<Object[]> callListAplica(@Param("docente_rfc") String docente_rfc);

    // Notación existsBy(Columna con primera mayúscula) proporcionada por JPA
    boolean existsByDocenteRFCRfc(@Param("docente_rfc") String docente_rfc);

}
