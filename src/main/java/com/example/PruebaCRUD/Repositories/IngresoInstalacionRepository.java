package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.IngresoInstalacion;
import com.example.PruebaCRUD.BD.PKCompuesta.IngresoInstalacionPK;
import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface IngresoInstalacionRepository extends JpaRepository<IngresoInstalacion, IngresoInstalacionPK> {

    @Query("SELECT DISTINCT new com.example.PruebaCRUD.DTO.IngresoInstalacionDTO(" +
            "a.boleta, " +
            "a.CURP.nombre, " +
            "a.CURP.apellido_p as apellidoP, " +
            "a.CURP.apellido_m as apellidoM, " +
            "e.id_ETS) " +
            "FROM InscripcionETS ie " +
            "JOIN ie.boletaIns a " +
            "JOIN ie.idETSIns e " +
            "WHERE a.boleta = :boleta AND e.id_ETS = :idETS")
    List<IngresoInstalacionDTO> findAlumnosInscritosETS(@Param("boleta") String boleta,
                                                        @Param("idETS") Integer idETS);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ingreso_instalacion (boleta, idets, fecha, hora) " +
            "VALUES (:boleta, :idets, :fecha, :hora)",
            nativeQuery = true)
    void saveAttendance(@Param("boleta") String boleta,
                        @Param("idets") Integer idETS,
                        @Param("fecha") Date fecha,
                        @Param("hora") Time hora);

    @Query("SELECT i FROM IngresoInstalacion i WHERE " +
            "i.id.boleta = :boleta AND " +
            "i.id.fecha = :fecha AND " +
            "CAST(i.id.idets AS integer) = :idETS")  // Conversión explícita
    List<IngresoInstalacion> findByBoletaAndFechaAndIdETS(
            @Param("boleta") String boleta,
            @Param("fecha") Date fecha,
            @Param("idETS") Integer idETS);

    // Método opcional para diagnóstico
    @Query(value = "SELECT idETS FROM ets WHERE idETS = :idETS", nativeQuery = true)
    Integer verificarExistenciaETS(@Param("idETS") Integer idETS);
}