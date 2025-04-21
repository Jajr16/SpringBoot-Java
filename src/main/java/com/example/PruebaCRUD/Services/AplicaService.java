package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.AplicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AplicaService {

    @Autowired
    private AplicaRepository aplicaRepository;

    public String obtenerRfcDocente(int idets) {
        return aplicaRepository.callObtenerDocenteRfc(idets);
    }
}
