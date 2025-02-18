package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.EscuelaProgramaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class EscuelaProgramaService {
    private final EscuelaProgramaRepository escuelaProgramaRepository;

    public EscuelaProgramaService(EscuelaProgramaRepository escuelaProgramaRepository) {
        this.escuelaProgramaRepository = escuelaProgramaRepository;
    }

    public List<?> getProgramasAcademicos(Integer escuela) {
        System.out.println("AQUI TENGO A " + escuela);
        return this.escuelaProgramaRepository.getEscuelaPrograma(escuela);
    }

    public List<?> getAllProgramasAcademicos() {
        return this.escuelaProgramaRepository.getEscuelasProgramas();
    }
}
