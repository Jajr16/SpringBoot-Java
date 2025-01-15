package com.example.PruebaCRUD.Sexo;

import com.example.PruebaCRUD.BD.Repositories.SexoRepository;
import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexoService {
    private final SexoRepository sexoRepository;

    @Autowired
    public SexoService(SexoRepository sexoRepository) {
        this.sexoRepository = sexoRepository;
    }

    public List<Sexo> getSexo() {
        return this.sexoRepository.findAll();
    }
}
