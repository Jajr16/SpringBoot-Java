package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.BD.ProgramaAcademico;
import com.example.PruebaCRUD.Repositories.EscuelaProgramaRepository;
import com.example.PruebaCRUD.Repositories.ProgramaAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

// El Service es el CRUD de la clase
/**
 * Clase que contendrá la lógica que para realizar las funciones principales de los endpoints
 */
@Service // Anotación que indica que esta clase es un servicio de negocio
public class ProgramaAcademicoService {
    private final EscuelaProgramaRepository escuelaProgramaRepository;

    @Autowired // Notación que permite inyectar dependencias
    public ProgramaAcademicoService(EscuelaProgramaRepository escuelaProgramaRepository) {
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
