package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaAlumnosRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    @Query(value = "SELECT * FROM ObtenerAsistenciaDetalles(:idets)", nativeQuery = true)
    List<Object[]> callObtenerAsistenciaDetalles(@Param("idets") Integer idets);


}
