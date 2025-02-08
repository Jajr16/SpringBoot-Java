package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.SexoRepository;
import com.example.PruebaCRUD.BD.Sexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class SexoService {
    private final SexoRepository sexoRepository;

    @Autowired // Notación que permite inyectar dependencias
    public SexoService(SexoRepository sexoRepository) {
        this.sexoRepository = sexoRepository;
    }

    public List<Sexo> getSexo() {
        return this.sexoRepository.findAll();
    }
}
