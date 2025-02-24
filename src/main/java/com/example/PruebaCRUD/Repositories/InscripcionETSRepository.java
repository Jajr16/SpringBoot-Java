package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.DTO.AlumnoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface InscripcionETSRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    @Query(value = "SELECT * FROM ListInscripcionesETS(:boleta)", nativeQuery = true)
    List<Object[]> callListInscripcionesETS(@Param("boleta") String boleta);

    boolean existsByBoletaInsBoleta(@Param("boleta") String boleta);

    @Query("SELECT a.boleta, p.Nombre, p.Apellido_P, p.Apellido_M " +
            "FROM InscripcionETS ie " +
            "JOIN ie.boletaIns a " +
            "JOIN ie.idETSIns e " +
            "JOIN a.CURP p " +
            "JOIN e.idPeriodo pe " +
            "WHERE e.Fecha = :fecha AND pe.idPeriodo = :periodo")
    List<Object[]>findAlumnosInscritosETS(@Param("fecha") Date fecha,
                                            @Param("periodo") Integer periodo);

}
