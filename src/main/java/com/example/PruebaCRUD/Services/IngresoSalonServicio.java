package com.example.PruebaCRUD.Services;


import com.example.PruebaCRUD.Repositories.IngresoSalonRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngresoSalonServicio {

    private final IngresoSalonRepositorio ingresoSalonRepositorio;

    @Autowired
    public IngresoSalonServicio(IngresoSalonRepositorio ingresoSalonRepositorio) {
        this.ingresoSalonRepositorio = ingresoSalonRepositorio;
    }

    public String verificarIngreso(Integer idets, String boleta) {
        return ingresoSalonRepositorio.verificarIngresoFuncion(boleta, idets) ? "existe" : "no existe";
    }

    public boolean eliminarReporte(Integer idets, String boleta) {
        return ingresoSalonRepositorio.eliminarReporteAlumno(idets, boleta);
    }
    
}
