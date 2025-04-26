package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.BD.Alumno;
import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes;
import com.example.PruebaCRUD.DTO.Saes.ListInsAlumnProjectionSaes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByBoleta(String Boleta);

    /**
     * En lugar de hacer la notación de findBy que nos proporciona JPA, se realiza una consulta más detallada y
     * personalizable con las clases del proyecto
     */
    @Query("""
            SELECT new com.example.PruebaCRUD.DTO.Saes.AlumnoDTOSaes(
                CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m) as nombre,
                a.boleta,
                pa.nombre,
                a.CorreoI
            ) FROM Alumno as a
            INNER JOIN Persona as p ON p.CURP = a.CURP.CURP
            INNER JOIN ProgramaAcademico as pa ON pa.idPA = a.idPA.idPA
            """)
    List<AlumnoDTOSaes> findAllAsDTO();

    @Query("""
            SELECT 
                a.boleta as boleta,
                CONCAT(p.nombre, ' ', p.apellido_p, ' ', p.apellido_m) as nombre
            FROM Persona p
            INNER JOIN Alumno a ON p.CURP = a.CURP.CURP
            WHERE p.unidadAcademica.id_Escuela = (SELECT p1.unidadAcademica.id_Escuela FROM Persona p1
                                            INNER JOIN Usuario u ON p1.CURP = u.CURP.CURP
                                            WHERE u.usuario = :usuario)
            """)
    List<ListInsAlumnProjectionSaes> findAlumnosSaes(String usuario);

    @Query("SELECT new com.example.PruebaCRUD.DTO.CredencialDTO(" +
            "a.imagenCredencial, " +
            "p.nombre, " +
            "p.apellido_p as apellidoP, " +
            "p.apellido_m as apellidoM, " +
            "a.boleta, " +
            "p.CURP as curp, " +
            "pa.nombre, " +
            "ua.nombre ) " +
            "FROM Alumno a " +
            "JOIN a.CURP p " +
            "JOIN a.idPA pa " +
            "JOIN EscuelaPrograma ep ON ep.idPAcad = pa " +
            "JOIN ep.idUA ua " +
            "WHERE a.boleta = :boleta")
    List<CredencialDTO> findbyBoleta(@Param("boleta") String boleta);

    @Query(value = "SELECT * FROM buscardatosestudiante(:boleta)", nativeQuery = true)
    List<Object[]> callAlumnoEspecificoData(@Param("boleta") String boleta);

    @Query("SELECT a.imagenCredencial FROM Alumno a WHERE a.boleta = :boleta")
    String findRutaImagenByBoleta(@Param("boleta") String boleta);

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
