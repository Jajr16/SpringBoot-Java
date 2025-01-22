package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscripcionETSRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    @Query(value = "SELECT * FROM ListInscripcionesETS(:boleta)", nativeQuery = true)
    List<Object[]> callListInscripcionesETS(@Param("boleta") String boleta);

}
