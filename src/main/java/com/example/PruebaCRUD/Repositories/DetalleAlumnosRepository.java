package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleAlumnosRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    @Query("SELECT new com.example.PruebaCRUD.DTO.DetalleAlumnosDTO(" +
            "a.imagenCredencial, " +
            "p.nombre as nombreAlumno, " +
            "p.apellido_p as apellidoPAlumno, " +
            "p.apellido_m as apellidoMAlumno, " +
            "a.boleta, " +
            "e.idUA.nombre as nombreETS, " +
            "e.Turno.nombre as nombreTurno, " +
            "s.numSalonSETS.numSalon as salon, " +
            "e.Fecha as fecha, " +
            "pp.nombre as nombreDocente, " +
            "pp.apellido_p as apellidoPDocente, " +
            "pp.apellido_m as apellidoMDocente) " +
            "FROM InscripcionETS i " +
            "JOIN i.boletaIns a " +
            "JOIN a.CURP p " +
            "JOIN i.idETSIns e " +
            "LEFT JOIN SalonETS s ON s.idETSSETS.id_ETS = e.id_ETS " +
            "LEFT JOIN Aplica ap ON ap.idETS.id_ETS = e.id_ETS " +
            "LEFT JOIN ap.docenteRFC pa " +
            "LEFT JOIN pa.CURP pp " +
            "WHERE a.boleta = :boleta")
    List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(@Param("boleta") String boleta);
}
