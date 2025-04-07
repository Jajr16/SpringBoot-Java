package com.example.PruebaCRUD.Services;


import com.example.PruebaCRUD.BD.IngresoSalon;
import com.example.PruebaCRUD.Repositories.IngresoSalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngresoSalonService {

    private final IngresoSalonRepository ingresoSalonRepository;

    @Autowired
    public IngresoSalonService(IngresoSalonRepository ingresoSalonRepository) {
        this.ingresoSalonRepository = ingresoSalonRepository;
    }

    public String verificarIngreso(Integer idets, String boleta) {
        return ingresoSalonRepository.verificarIngresoFuncion(boleta, idets) ? "existe" : "no existe";
    }

    public boolean eliminarReporte(Integer idets, String boleta) {
        return ingresoSalonRepository.eliminarReporteAlumno(idets, boleta);
    }
    
}
