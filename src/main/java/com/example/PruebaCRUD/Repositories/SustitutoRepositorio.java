package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.Reemplazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SustitutoRepositorio extends JpaRepository<Reemplazo, AplicaPK> {
    @Query("SELECT r FROM Reemplazo r WHERE r.id.DocenteRFC = :docenteRFC")
    List<Reemplazo> findByDocenteRfc(@Param("docenteRFC") String docenteRFC);

    @Query("SELECT r FROM Reemplazo r WHERE r.estatus = :estatus")
    List<Reemplazo> findByEstatus(@Param("estatus") Integer estatus);
}
