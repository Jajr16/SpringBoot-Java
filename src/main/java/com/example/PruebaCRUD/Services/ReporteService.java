package com.example.PruebaCRUD.Services;


import com.example.PruebaCRUD.DTO.ReporteSqlDTO;
import com.example.PruebaCRUD.Repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<ReporteSqlDTO> obtenerDatosReporte(Integer idets, String boleta) {
        List<Object[]> resultados = alumnoRepository.obtenerDatosReporte(idets, boleta);
        List<ReporteSqlDTO> reportes = new ArrayList<>();

        for (Object[] resultado : resultados) {
            ReporteSqlDTO reporte = new ReporteSqlDTO();
            reporte.setCurp((String) resultado[0]);
            reporte.setNombre((String) resultado[1]);
            reporte.setApellidoP((String) resultado[2]);
            reporte.setApellidoM((String) resultado[3]);
            reporte.setEscuela((String) resultado[4]);
            reporte.setCarrera((String) resultado[5]);
            reporte.setPeriodo((String) resultado[6]);
            reporte.setTipo((String) resultado[7]);
            reporte.setTurno((String) resultado[8]);
            reporte.setMateria((String) resultado[9]);
            reporte.setFechaIngreso((Date) resultado[10]);
            reporte.setHoraIngreso((Time) resultado[11]);
            reporte.setNombreDocente((String) resultado[12]);
            reporte.setTipoEstado((String) resultado[13]);
            reporte.setPresicion(((Number) resultado[14]).floatValue());
            reporte.setMotivo((String) resultado[15]);
            reportes.add(reporte);
        }

        return reportes;
    }

    public String obtenerImagenAlumno(Integer idets, String boleta) {
        return alumnoRepository.obtenerImagenAlumno(idets, boleta);
    }
}




