package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.IngresoInstalacion;
import com.example.PruebaCRUD.BD.PKCompuesta.IngresoInstalacionPK;
import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IngresoInstalacionRepository extends JpaRepository<IngresoInstalacion, IngresoInstalacionPK> {

    @Query("SELECT DISTINCT new com.example.PruebaCRUD.DTO.IngresoInstalacionDTO(" +
            "a.boleta, " +
            "a.CURP.nombre, " +
            "a.CURP.apellido_p as apellidoP, " +
            "a.CURP.apellido_m as apellidoM, " +
            "CAST(e.id_ETS AS string)) " +
            "FROM InscripcionETS ie " +
            "JOIN ie.boletaIns a " +
            "JOIN ie.idETSIns e " +
            "WHERE a.boleta = :boleta")
    List<IngresoInstalacionDTO> findAlumnosInscritosETS(@Param("boleta") String boleta);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ingreso_instalacion (boleta, idets, fecha, hora) VALUES (:boleta, :idETS, :fecha, :hora)",
            nativeQuery = true)
    void saveAttendance(@Param("boleta") String boleta,
                        @Param("idETS") Integer idETS,
                        @Param("fecha") Date fecha,
                        @Param("hora") Time hora);
}