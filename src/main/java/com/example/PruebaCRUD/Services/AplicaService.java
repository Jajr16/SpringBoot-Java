package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.AplicaRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AplicaService {

    @Autowired
    private AplicaRepository2 aplicaRepository;

    public String obtenerRfcDocente(int idets) {
        return aplicaRepository.callObtenerDocenteRfc(idets);
    }
}
