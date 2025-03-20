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
            "a.imagenCredencial, " + // Foto de la credencial
            "p.nombre as nombreAlumno," +
            "p.apellido_p as apellidoPAlumno, " +
            "p.apellido_m as apellidoMAlumno, " + // Nombre completo del alumno
            "a.boleta, " +
            "e.idUA.nombre as nombreETS, " + // Nombre del ETS
            "e.Turno.nombre as nombreTurno, " + // Nombre del turno
            "s.numSalonSETS.numSalon as salon, " + // Salón
            "e.Fecha as fecha, " + // Fecha
            "pp.nombre as nombreDocente, " + // Nombre del docente
            "pp.apellido_p as apellidoPDocente, " + // Apellido paterno del docente
            "pp.apellido_m as apellidoMDocente) " + // Apellido materno del docente
            "FROM InscripcionETS i " +
            "JOIN i.boletaIns a " + // Relación con Alumno
            "JOIN a.CURP p " + // Relación con Persona
            "JOIN i.idETSIns e " + // Relación con ETS
            "JOIN SalonETS s ON s.idETSSETS.id_ETS = e.id_ETS " + // Relación con SalonETS
            "JOIN Aplica ap ON ap.idETS.id_ETS = e.id_ETS " + // Relación con Aplica
            "JOIN ap.docenteRFC pa " + // Relación con Docente
            "JOIN pa.CURP pp " + // Relación con Persona del docente
            "WHERE a.boleta = :boleta") // Filtro por boleta
    List<DetalleAlumnosDTO> findDetalleAlumnoporboleta(@Param("boleta") String boleta);
}
