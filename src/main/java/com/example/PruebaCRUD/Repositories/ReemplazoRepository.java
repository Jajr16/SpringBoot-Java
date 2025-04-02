package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.PKCompuesta.AplicaPK;
import com.example.PruebaCRUD.BD.Reemplazo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReemplazoRepository extends JpaRepository<Reemplazo, AplicaPK> {
    @Query("SELECT r FROM Reemplazo r WHERE r.id.DocenteRFC = :docenteRFC")
    List<Reemplazo> findByDocenteRfc(@Param("docenteRFC") String docenteRFC);
}
