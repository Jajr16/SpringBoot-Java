package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.InscripcionETS;
import com.example.PruebaCRUD.BD.PKCompuesta.InscripcionETSPK;
import com.example.PruebaCRUD.DTO.AlumnoDTO;
import com.example.PruebaCRUD.DTO.DetalleAlumnosDTO;
import com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InscripcionETSRepository extends JpaRepository<InscripcionETS, InscripcionETSPK> {

    @Query(value = "SELECT * FROM ListInscripcionesETS(:boleta)", nativeQuery = true)
    List<Object[]> callListInscripcionesETS(@Param("boleta") String boleta);

    boolean existsByBoletaInsBoleta(@Param("boleta") String boleta);

    @Query("SELECT DISTINCT new com.example.PruebaCRUD.DTO.AlumnoDTO(" +
            "a.boleta, " +
            "p.nombre, " +
            "p.apellido_p as apellidoP, " +
            "p.apellido_m as apellidoM, " +
            "t.nombre as turno) " +
            "FROM InscripcionETS ie " +
            "JOIN ie.boletaIns a " +
            "JOIN a.CURP p " +
            "JOIN ie.idETSIns e " +
            "JOIN e.idPeriodo pe " +
            "JOIN e.Turno t " +
            "WHERE e.Fecha = :fecha " +
            "AND :fecha BETWEEN :fechaInicio AND :fechaFin " +
            "AND pe.periodo = :periodo")
    List<AlumnoDTO> findAlumnosInscritosETS(@Param("fecha") Date fecha, @Param("fechaInicio") Date fechaInicio,
                                            @Param("fechaFin") Date fechaFin, @Param("periodo") String periodo);

    boolean existsById(InscripcionETSPK id);

    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.InscripcionesDTOSaes(
                ua.nombre as materia,
                t.nombre as turno,
                e.Fecha as fecha,
                e.Cupo as cupo,
                pets.periodo as periodo,
                CONCAT(a.CURP.nombre, ' ', a.CURP.apellido_p, ' ', a.CURP.apellido_m) as nombre
            ) FROM InscripcionETS ie
            INNER JOIN ETS e ON e.id_ETS = ie.idETSIns.id_ETS
            INNER JOIN Alumno a ON a.boleta = ie.boletaIns.boleta
            INNER JOIN UnidadAprendizaje ua ON e.idUA.idUA = ua.idUA
            INNER JOIN periodoETS pets ON pets.idPeriodo = e.idPeriodo.idPeriodo
            INNER JOIN Turno t ON t.idTurno = e.Turno.idTurno
            INNER JOIN ProgramaAcademico pa ON ua.idPA.idPA = pa.idPA
            INNER JOIN EscuelaPrograma ep ON pa.idPA = ep.idPAcad.idPA
            WHERE ep.idUA.id_Escuela = (SELECT p.unidadAcademica.id_Escuela FROM Persona p
                                            INNER JOIN Usuario u ON p.CURP = u.CURP.CURP
                                            WHERE u.usuario = :usuario)
            """)
    List<InscripcionesDTOSaes> getInscripciones(@Param("usuario") String usuario);

    @Query("SELECT DISTINCT ie.id.Boleta, ie.id.idETS FROM InscripcionETS ie")
    List<Object[]> findDistinctBoletaIdets();

    @Query("SELECT new com.example.PruebaCRUD.DTO.DetalleAlumnosDTO(" +
            "p.nombre as nombreAlumno, " +
            "p.apellido_p as apellidoPAlumno, " +
            "p.apellido_m as apellidoMAlumno, " +
            "a.boleta, " +
            "e.idUA.nombre as nombreETS, " +
            "e.id_ETS as idETS, " +
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

    @Query(value = "SELECT * FROM obtenerasistenciadetalles(:idets)", nativeQuery = true)
    List<Object[]> callObtenerAsistenciaDetalles(@Param("idets") Integer idets);
}
