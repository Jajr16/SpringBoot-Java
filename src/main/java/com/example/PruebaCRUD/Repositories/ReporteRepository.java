package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.ReporteSqlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ReporteRepository extends JpaRepository<Alumno, String> { // Usamos Object ya que no hay una entidad mapeada directamente

    @Query(value = """
        SELECT
            p.curp,
            p.nombre,
            p.apellido_p,
            p.apellido_m,
            ua.nombre AS escuela,
            pa.nombre AS carrera,
            pe.periodo,
            CAST(pe.tipo AS VARCHAR) AS tipo,
            t.nombre AS turno,
            ua2.nombre AS materia,
            isalon.fecha AS fecha_ingreso,
            isalon.hora AS hora_ingreso,
            isalon.docente AS nombre_docente,
            te.tipo AS tipo_estado,
            COALESCE(rn.precision, 0.0) AS presicion,
            COALESCE(mr.motivo, 'No Motivo') AS motivo
        FROM
            alumno a
        INNER JOIN
            persona p ON a.curp = p.curp
        INNER JOIN
            unidadacademica ua ON p.id_escuela = ua.id_escuela
        INNER JOIN
            programaacademico pa ON a.idpa = pa.idpa
        INNER JOIN
            ets e ON e.idets = :idets
        INNER JOIN
            periodoets pe ON e.id_periodo = pe.id_periodo
        INNER JOIN
            turno t ON e.turno = t.id_turno
        INNER JOIN
            unidadaprendizaje ua2 ON e.idua = ua2.idua
        INNER JOIN
            ingreso_salon isalon ON e.idets = isalon.idets AND a.boleta = isalon.boleta
        INNER JOIN
            tipo_estado te ON isalon.estado = te.idtipo
        LEFT JOIN
            resultadorn rn ON e.idets = rn.idets AND a.boleta = rn.boleta
        LEFT JOIN
            motivo_rechazo mr ON a.boleta = mr.boleta AND e.idets = mr.idets
        WHERE
            a.boleta = :boleta AND e.idets = :idets
    """, nativeQuery = true)
    List<Object[]> obtenerDatosReporte(@Param("idets") Integer idets, @Param("boleta") String boleta);


    @Query(value = "SELECT obtener_imagen_alumno(:idets, :boleta)", nativeQuery = true)
    String obtenerImagenAlumno(@Param("idets") Integer idets, @Param("boleta") String boleta);
}


