package com.example.PruebaCRUD.Repositories;

import com.example.PruebaCRUD.DTO.ReporteSqlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReporteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ReporteSqlDTO> obtenerDatosReporte(Integer idets, String boleta) {
        String sql = "SELECT * FROM obtener_datos_reporte(?, ?)";
        return jdbcTemplate.query(sql, new Object[]{idets, boleta}, (rs, rowNum) -> {
            ReporteSqlDTO reporte = new ReporteSqlDTO();
            reporte.setCurp(rs.getString("curp"));
            reporte.setNombre(rs.getString("nombre"));
            reporte.setApellidoP(rs.getString("apellido_p"));
            reporte.setApellidoM(rs.getString("apellido_m"));
            reporte.setEscuela(rs.getString("escuela"));
            reporte.setCarrera(rs.getString("carrera"));
            reporte.setPeriodo(rs.getString("periodo"));
            reporte.setTipo(rs.getString("tipo"));
            reporte.setTurno(rs.getString("turno"));
            reporte.setMateria(rs.getString("materia"));
            reporte.setFechaIngreso(rs.getDate("fecha_ingreso"));
            reporte.setHoraIngreso(rs.getTime("hora_ingreso"));
            reporte.setNombreDocente(rs.getString("nombre_docente"));
            reporte.setTipoEstado(rs.getString("tipo_estado"));
            reporte.setPresicion(rs.getFloat("presicion"));
            reporte.setMotivo(rs.getString("motivo"));
            return reporte;
        });
    }

    public String obtenerImagenAlumno(Integer idets, String boleta) {
        String sql = "SELECT obtener_imagen_alumno(?, ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idets, boleta}, String.class);
    }
}


