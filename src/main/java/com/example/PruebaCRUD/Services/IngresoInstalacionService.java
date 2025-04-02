package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.IngresoInstalacionDTO;
import com.example.PruebaCRUD.Repositories.IngresoInstalacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoInstalacionService {

    @Autowired
    private IngresoInstalacionRepository ingresoInstalacionRepository;

    public List<IngresoInstalacionDTO> getAlumnosInscritosETS(String boleta) {
        return ingresoInstalacionRepository.findAlumnosInscritosETS(boleta);
    }
}