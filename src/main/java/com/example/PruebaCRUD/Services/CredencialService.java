package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.DTO.CredencialDTO;
import com.example.PruebaCRUD.Repositories.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredencialService {
    @Autowired
    private CredencialRepository credencialRepository;

    public List<CredencialDTO> findCredencialPorBoleta(String boleta) {
        System.out.println("LA BOLETA ES: " + boleta);
        System.out.println("LA FUNCION DE ALE ES: " + credencialRepository.findbyBoleta(boleta));
        return credencialRepository.findbyBoleta(boleta);
    }
}
