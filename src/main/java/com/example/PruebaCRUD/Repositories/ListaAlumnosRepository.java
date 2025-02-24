package com.example.PruebaCRUD.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListaAlumnosRepository {

    @Query(value = "SELECT * FROM ObtenerAsistenciaDetalles(:idets)", nativeQuery = true)
    List<Object[]> callObtenerAsistenciaDetalles(@Param("idets") String idets);
    

}
